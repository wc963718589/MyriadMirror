package com.example.myriadmirror.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.ChatMessageReceiveTextColor
import com.example.myriadmirror.ui.theme.ChatMessageSendTextColor
import com.example.myriadmirror.ui.theme.DropdownMenuColor
import com.example.myriadmirror.ui.theme.DropdownTextColor
import kotlinx.coroutines.launch

@Composable
fun SingleDeleteDropdownMenu(
    onDeleteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(DropdownMenuColor)
            .clickable {
                onDeleteClick.invoke()
            }
            .padding(horizontal = 6.dp, vertical = 8.dp)) {
        Text(
            text = stringResource(R.string.delete),
            color = DropdownTextColor,
            fontSize = 14.sp,
            )
        }
}

@Preview
@Composable
fun SingleDeleteDropdownMenuPreview() {
    SingleDeleteDropdownMenu()
}

