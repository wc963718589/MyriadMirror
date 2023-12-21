package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SettingScreen() {
    Text(text = "设置界面", modifier = Modifier.fillMaxSize())
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen()
}
