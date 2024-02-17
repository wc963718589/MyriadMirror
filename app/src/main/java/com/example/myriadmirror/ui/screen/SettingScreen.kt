package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.NormalBackgroundColor
import com.example.myriadmirror.ui.view.SettingItem


@Composable
fun SettingScreen(
    onApiKeyClick: () -> Unit = {},
    onModelClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            SettingItem(title = stringResource(R.string.setting_api_key_title), onItemClick = onApiKeyClick)
            Spacer(modifier = Modifier.height(16.dp))
            SettingItem(title = stringResource(R.string.setting_model_title), onItemClick = onModelClick)
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen()
}
