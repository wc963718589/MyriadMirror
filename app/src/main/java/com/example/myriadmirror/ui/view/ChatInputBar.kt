package com.example.myriadmirror.ui.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.ActiveColor
import com.example.myriadmirror.ui.theme.BottomNavigationBackgroundColor
import com.example.myriadmirror.ui.theme.InactiveColor
import com.example.myriadmirror.ui.theme.TextColorWhite
import com.example.myriadmirror.ui.theme.TextInputBackgroundColor
import com.example.myriadmirror.util.clickableWithoutRipple

@Composable
fun ChatInputBar(
    value: String,
    onSendClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    val enable = value.isNotEmpty()
    Surface(shadowElevation = 10.dp) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .background(BottomNavigationBackgroundColor)
                .defaultMinSize(
                    minHeight = 56.dp
                )
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(TextInputBackgroundColor)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    maxLines = 6,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 1.5.em
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            Box(modifier = Modifier
                .clickableWithoutRipple {
                    if (enable) {
                        onSendClick()
                    }
                }
                .alpha(if (enable) 1f else 0.7f)
                .padding(start = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ActiveColor)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                Text(
                    text = stringResource(R.string.send),
                    fontSize = 16.sp,
                    lineHeight = 1.5.em,
                    color = TextColorWhite
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatInputBarPreview() {
    ChatInputBar(
        value = "asdasdadsasdasdsdasdasdasdasdasdasda对方水电" +
                "费水电费第三方1发的发撒大声地啊大萨达" +
                "费水电费第三方1发的发撒大声地啊大萨达" +
                "费水电费第三方1发的发撒大声地啊大萨达" +
                "费水电费第三方1发的发撒大声地啊大萨达" +
                "费水电费第三方1发的发撒大声地啊大萨达" +
                "费水电费第三方1发的发撒大声地啊大萨达"
    ) {}
}

