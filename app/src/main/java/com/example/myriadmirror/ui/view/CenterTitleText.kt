package com.example.myriadmirror.ui.view


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.AppBarTitleColor

@Composable
fun CenterTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = AppBarTitleColor,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview
@Composable
fun CenterTitleTextPreview() {
    CenterTitleText("密钥设置")
}
