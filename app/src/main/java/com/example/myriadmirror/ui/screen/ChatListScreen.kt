package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.NormalBackgroundColor
import com.example.myriadmirror.ui.view.ChatListItem
import com.example.myriadmirror.ui.view.DataEmptyHint
import com.example.myriadmirror.viewModel.ChatListViewModel
import com.example.myriadmirror.viewModel.ViewModelProvider


@Composable
fun ChatListScreen(
    onChatClick: (id: Int) -> Unit = {},
    viewModel: ChatListViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val chatListUiState by viewModel.chatListUiState.collectAsState()

    Surface(color = NormalBackgroundColor, modifier = Modifier.fillMaxSize()) {
        if (chatListUiState.chatItemList.isEmpty())
            DataEmptyHint(stringResource(R.string.chat_list_empty_hint))
        else
            LazyColumn {
                items(chatListUiState.chatItemList) { data ->
                    ChatListItem(data = data, onItemClick = onChatClick)
                }
            }
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatListScreen()
}
