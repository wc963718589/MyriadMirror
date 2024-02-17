package com.example.myriadmirror.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.model.ChatItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {
    val chatListUiState: StateFlow<ChatListUiState> =
        chatRepository.getAllChatItemsStream().map { ChatListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ChatListUiState()
            )

    suspend fun deleteChat(chatItem: ChatItemData) {
        chatRepository.deleteChatItem(chatItem)
    }
}

data class ChatListUiState(val chatItemList: List<ChatItemAndRole> = listOf())