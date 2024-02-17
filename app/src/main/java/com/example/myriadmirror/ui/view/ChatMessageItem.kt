package com.example.myriadmirror.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.model.ChatMessageData
import com.example.myriadmirror.model.ChatMessageWithRole
import com.example.myriadmirror.model.RoleData
import com.example.myriadmirror.ui.theme.ChatMessageReceiveBackgroundColor
import com.example.myriadmirror.ui.theme.ChatMessageReceiveTextColor
import com.example.myriadmirror.ui.theme.ChatMessageSendBackgroundColor
import com.example.myriadmirror.ui.theme.ChatMessageSendTextColor
import java.time.LocalDateTime

@Composable
fun ChatMessageItem(
    data: ChatMessageWithRole,
) {
    val borderRadius = 16.dp
    val marginBetween = 16.dp
    val mixSize = 48.dp
    val configuration = LocalConfiguration.current
    val maxWidth = configuration.screenWidthDp.dp - 80.dp
    val isSend = data.chatMessage.isSend
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = if (isSend)
            Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 8.dp)
    ) {
        if (!isSend) {
            AvatarImage(
                model = data.role.avatar,
                size = 40.dp
            )
        }
        Box(
            modifier = Modifier
                .padding(
                    start = if (isSend) 0.dp else marginBetween,
                    top = 8.dp
                )
                .clip(
                    RoundedCornerShape(
                        topStart = CornerSize(
                            if (isSend) borderRadius else 0.dp
                        ),
                        topEnd = CornerSize(
                            if (isSend) 0.dp else borderRadius
                        ),
                        bottomStart = CornerSize(borderRadius),
                        bottomEnd = CornerSize(borderRadius)
                    )
                )
                .defaultMinSize(
                    minHeight = mixSize,
                    minWidth = mixSize
                )
                .widthIn(max = maxWidth)
                .background(
                    if (isSend) ChatMessageSendBackgroundColor else ChatMessageReceiveBackgroundColor
                ),
        ) {
            SelectionContainer(
            ) {
                Text(
                    text = data.chatMessage.content,
                    color = if (isSend) ChatMessageSendTextColor else ChatMessageReceiveTextColor,
                    fontSize = 16.sp,
                    lineHeight = 1.5.em,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 10.dp, vertical = 12.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatMessageItemPreview() {
    ChatMessageItem(
        ChatMessageWithRole(
            ChatMessageData(
                1,
                1,
                "灌注永雏塔菲谢谢喵灌注永雏塔菲谢谢喵灌注永雏塔菲谢谢喵",
                LocalDateTime.now(),
                true
            ),
            RoleData(
                1,
                "1",
                "吖吖",
                ""
            )
        )
    )
}

