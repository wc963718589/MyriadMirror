package com.example.myriadmirror.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.model.ChatItemData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ChatListViewModel(
    private val chatRepository: ChatRepository
): ViewModel() {
    val chatListUiState: StateFlow<ChatListUiState> =
        chatRepository.getAllChatItemsStream().map { ChatListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ChatListUiState()
            )
//    val data = ChatItemData(
//        "test",
//        "https://pub-45c0b529c25a4d388dfa7cf57f35f8f0.r2.dev/avatar/xhs.webp",
//        "永雏塔菲",
//        "灌注永雏塔菲谢谢喵~",
//        "11:45"
//    )
//    val chatList = listOf<ChatItemData>(
//        data, data, data, data, data, data, data, data, data, data, data, data
//    )
}

data class ChatListUiState(val chatItemList: List<ChatItemAndRole> = listOf())