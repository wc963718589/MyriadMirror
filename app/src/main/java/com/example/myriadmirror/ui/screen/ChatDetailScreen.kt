package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.AppBarTitleColor
import com.example.myriadmirror.ui.theme.NormalIconColor
import com.example.myriadmirror.ui.theme.TextColorBlack
import com.example.myriadmirror.ui.theme.TextColorGrey
import com.example.myriadmirror.ui.theme.TextColorRed
import com.example.myriadmirror.ui.view.CenterTitleText
import com.example.myriadmirror.ui.view.ChatInputBar
import com.example.myriadmirror.ui.view.ChatMessageItem
import com.example.myriadmirror.ui.view.ClearDialog
import com.example.myriadmirror.ui.view.CommonAppBar
import com.example.myriadmirror.ui.view.SingleDeleteDropdownMenu
import com.example.myriadmirror.util.autoCloseKeyboard
import com.example.myriadmirror.util.clickableWithoutRipple
import com.example.myriadmirror.viewModel.ChatDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatDetailScreen(
    onBackClick: () -> Unit = {},
    onOptionClick: (Int) -> Unit = {},
    viewModel: ChatDetailViewModel = hiltViewModel()
) {
    val roleDetailUiState by viewModel.roleDetailUiState.collectAsState()
    val messagesUiState = viewModel.messages.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    var clearDialogState by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.autoCloseKeyboard(LocalSoftwareKeyboardController.current),
        topBar = {
            CommonAppBar(
                onBackClick = onBackClick,
                actionIcon = {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_clear),
                            contentDescription = stringResource(R.string.clear_messages),
                            tint = NormalIconColor,
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    clearDialogState = true
                                }
                                .padding(8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.icon_option),
                            contentDescription = stringResource(R.string.set_role),
                            tint = NormalIconColor,
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    onOptionClick.invoke(viewModel.roleId)
                                }
                                .padding(8.dp)
                        )
                    }
                }
            ) {
                CenterTitleText(
                    text = roleDetailUiState.roleData.name.ifEmpty { stringResource(R.string.empty_name_default) },
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 20.dp, start = 40.dp)
                )
            }
        },
        bottomBar = {
            ChatInputBar(
                value = viewModel.sendMessageValue,
                onSendClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        viewModel.sendMessage()
                    }
                }
            ) { value ->
                viewModel.sendMessageValue = value
            }
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Bottom,
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = 12.dp)
        ) {
            items(messagesUiState) { state ->
                state?.let { data ->
                    ChatMessageItem(data = data.chatMessageWithRole)
                }
            }
        }
        ClearDialog(
            state = clearDialogState,
            onDismiss = { clearDialogState = false }) {
            clearDialogState = false
            coroutineScope.launch(Dispatchers.IO) {
                viewModel.clearMessage()
            }
        }
    }
}

@Preview
@Composable
fun ChatDetailScreenPreview() {
    ChatDetailScreen()
}

