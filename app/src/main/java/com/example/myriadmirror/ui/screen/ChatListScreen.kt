package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.view.ChatListItem
import com.example.myriadmirror.ui.view.DataEmptyHint
import com.example.myriadmirror.ui.view.SingleDeleteDropdownMenu
import com.example.myriadmirror.viewModel.ChatListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ChatListScreen(
    onChatClick: (id: Int) -> Unit = {},
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val chatListUiState by viewModel.chatListUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        if (chatListUiState.chatItemList.isEmpty())
            DataEmptyHint(stringResource(R.string.chat_list_empty_hint))
        else
            LazyColumn {
                items(chatListUiState.chatItemList) { data ->
                    ChatListItem(data = data, onItemClick = { onChatClick(data.role.roleId) }) {
                        SingleDeleteDropdownMenu(
                            onDeleteClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    viewModel.deleteChat(data.chatItem)
                                }
                            }
                        )
                    }
                }
            }
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatListScreen()
}
