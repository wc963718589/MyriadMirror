package com.example.myriadmirror.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.model.ChatConfig
import com.example.myriadmirror.model.ChatMessageData
import com.example.myriadmirror.model.ChatMessageWithRole
import com.example.myriadmirror.model.Message
import com.example.myriadmirror.model.toListMap
import com.example.myriadmirror.network.NetworkUtil
import com.example.myriadmirror.network.NetworkUtil.executeWithRetry
import com.example.myriadmirror.network.chatCompletionsBody
import com.example.myriadmirror.util.Constants
import com.example.myriadmirror.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository
) : ViewModel() {
    val roleId: Int = savedStateHandle.get<Int>(Screen.roleId)!!
    private val contextTokens = mutableListOf<Message>()
    var sendMessageValue by mutableStateOf("")

    var roleDetailUiState: StateFlow<RoleDetailUiState> =
        chatRepository.getRoleSteam(roleId)
            .map {
                if (it == null)
                    RoleDetailUiState()
                else
                    RoleDetailUiState(it)
            }
            .filterNotNull()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = RoleDetailUiState()
            )

    val messages: Flow<PagingData<MessagesUiState>> =
        Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
        ) {
            chatRepository.getAllChatMessagesStream(roleId)
        }
            .flow
            .map { pagingData ->
                pagingData.map { MessagesUiState(it) }
            }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val tokens = chatRepository.getContextTokens(roleId, Constants.TOKEN_SIZE)
                .map {
                    it.toMessage()
                }
            contextTokens.addAll(tokens)
        }
        viewModelScope.launch(Dispatchers.IO) {
            roleDetailUiState.collect {
                updateSystemToken()
            }
        }
    }

    private fun updateSystemToken() {
        val content = roleDetailUiState.value.roleData.roleDescription.ifEmpty {
            Constants.DEFAULT_CONTENT
        }
        if (contextTokens.isEmpty() || contextTokens.first().role != Constants.TYPE_SYSTEM) {
            contextTokens.add(
                0, Message(
                    role = Constants.TYPE_SYSTEM,
                    content = content
                )
            )
        } else {
            contextTokens.first().content = content
        }
    }

    private fun chatCompletions(message: String) {
        addToken(
            Message(
                role = Constants.TYPE_USER,
                content = message
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            NetworkUtil.api.chatCompletions(
                chatCompletionsBody(
                    model = ChatConfig.model,
                    messages = contextTokens.toListMap(),
                    temperature = ChatConfig.temperature,
                    topP = ChatConfig.topP
                )
            ).executeWithRetry()
                .onSuccess {
                    for (choice in it.choices) {
                        addToken(choice.message)
                        receiveMessage(choice.message.content)
                    }
                }
                .onFailure {
                    receiveMessage(it.message ?: "未知错误", true)
                }
        }
    }

    private fun addToken(message: Message) {
        if (contextTokens.size >= Constants.TOKEN_SIZE + 1) {
            contextTokens.removeAt(1)
        }
        contextTokens.add(message)
    }

    suspend fun sendMessage() {
        chatRepository.insertChatMessage(
            ChatMessageData(
                roleId = roleId,
                content = sendMessageValue,
                time = LocalDateTime.now(),
                isSend = true
            )
        )
        chatCompletions(sendMessageValue)
        sendMessageValue = ""
    }

    private suspend fun receiveMessage(message: String, isError: Boolean = false) {
        chatRepository.insertChatMessage(
            ChatMessageData(
                roleId = roleId,
                content = message,
                time = LocalDateTime.now(),
                isSend = false,
                isError = isError
            )
        )
    }

    suspend fun deleteMessage(chatMessageWithRole: ChatMessageWithRole) {
        chatRepository.deleteChatMessage(
            chatMessageWithRole,
            contextTokens.last().equals(chatMessageWithRole)
        )
    }

    suspend fun clearMessage() {
        chatRepository.deleteChatMessageByRoleId(roleId)
    }
}

data class MessagesUiState(val chatMessageWithRole: ChatMessageWithRole)