package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.AppBarTitleColor
import com.example.myriadmirror.ui.theme.NormalBackgroundColor
import com.example.myriadmirror.ui.theme.NormalIconColor
import com.example.myriadmirror.ui.view.CenterTitleText
import com.example.myriadmirror.ui.view.CommonAppBar
import com.example.myriadmirror.ui.view.SettingTextField
import com.example.myriadmirror.util.autoCloseKeyboard
import com.example.myriadmirror.util.clickableWithoutRipple
import com.example.myriadmirror.viewModel.ApiKeySettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ApiKeySettingScreen(
    navigationUp: () -> Unit = {},
    viewModel: ApiKeySettingViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val apiKeySettingUiState = viewModel.apiKeySettingUiState.collectAsState()
    Scaffold(
        modifier = Modifier.autoCloseKeyboard(LocalSoftwareKeyboardController.current),
        topBar = {
            CommonAppBar(
                onBackClick = navigationUp,
                actionIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_confirm),
                        contentDescription = stringResource(R.string.confirm_api_key_setting),
                        tint = NormalIconColor,
                        modifier = Modifier
                            .clickableWithoutRipple {
                                coroutineScope.launch(Dispatchers.IO) {
                                    viewModel.saveSetting()
                                    withContext(Dispatchers.Main) {
                                        navigationUp.invoke()
                                    }
                                }
                            }
                            .padding(8.dp)
                    )
                }
            ) {
                CenterTitleText(
                    text = stringResource(R.string.setting_api_key_title),
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 20.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SettingTextField(
                value = apiKeySettingUiState.value.baseUrl,
                label = stringResource(R.string.set_base_url_title),
                placeholder = stringResource(R.string.set_base_url_hint),
            ) { value ->
                viewModel.updateUrl(value)
            }
            SettingTextField(
                value = apiKeySettingUiState.value.apiKey,
                label = stringResource(R.string.set_api_key_title),
                placeholder = stringResource(R.string.set_api_key_hint),
            ) { value ->
                viewModel.updateKey(value)
            }
        }
    }
}

@Preview
@Composable
fun ApiKeySettingScreenPreview() {
    ApiKeySettingScreen()
}

