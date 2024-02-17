package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.DropdownMenuColor
import com.example.myriadmirror.ui.theme.DropdownTextColor
import com.example.myriadmirror.ui.theme.NormalIconColor
import com.example.myriadmirror.ui.theme.TextColorGrey
import com.example.myriadmirror.ui.view.CenterTitleText
import com.example.myriadmirror.ui.view.CommonAppBar
import com.example.myriadmirror.ui.view.TitleSettingSlider
import com.example.myriadmirror.util.Constants
import com.example.myriadmirror.util.autoCloseKeyboard
import com.example.myriadmirror.util.clickableWithoutRipple
import com.example.myriadmirror.util.crop
import com.example.myriadmirror.viewModel.ModelSettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ModelSettingScreen(
    navigationUp: () -> Unit = {},
    viewModel: ModelSettingViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val modelSettingUiState = viewModel.modelSettingUiState.collectAsState()
    val inputState = viewModel.inputUiState.collectAsState()
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
            ModelSelector(
                value = modelSettingUiState.value.model,
                onValueChange = { value ->
                    viewModel.updateModel(value)
                },
                models = viewModel.modelsList
            ) {
                viewModel.updateModel(it)
            }

            Spacer(modifier = Modifier.padding(top = 12.dp))

            TitleSettingSlider(
                title = stringResource(R.string.temperature_title),
                value = modelSettingUiState.value.temperature,
                items = Constants.SETTING_VALUES,
                onSliderValueChange = {value ->
                    viewModel.updateSliderTemperature(value)
                },
                calibrations = Constants.TEMPERATURE_CALIBRATIONS,
                enableInput = true,
                textFieldValue = inputState.value.temperature,
                onTextFieldValueChange = { value ->
                    viewModel.updateInputTemperature(value)
                }
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.temperature_left_label),
                        fontSize = 14.sp,
                        color = TextColorGrey)
                    Text(text = stringResource(R.string.temperature_right_label),
                        fontSize = 14.sp,
                        color = TextColorGrey)
                }
            }

            TitleSettingSlider(
                title = stringResource(R.string.top_p_title),
                value = modelSettingUiState.value.topP,
                items = Constants.SETTING_VALUES,
                onSliderValueChange = {value ->
                    viewModel.updateSliderTopP(value)
                },
                enableInput = true,
                textFieldValue = inputState.value.topP,
                onTextFieldValueChange = { value ->
                    viewModel.updateInputTopP(value)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelSelector(
    value: String,
    onValueChange: (String) -> Unit,
    models: List<String>,
    onItemClick: (String) -> Unit = {}
) {
    var popUpState by remember { mutableStateOf(false) }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(R.string.set_model_title),
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 1.5.em
            ),
            readOnly = true,
            maxLines = 4,
            modifier = Modifier
                .indication(MutableInteractionSource(), LocalIndication.current)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .focusRequester(focusRequester)
                .onSizeChanged {
                    itemWidth = with(density) { it.width.toDp() }
                }
        )
        // 用于接收点击事件
        if (!popUpState) {
            Box(modifier = Modifier
                .matchParentSize()
                .clickableWithoutRipple {
                    popUpState = true
                    focusRequester.requestFocus()
                })
        }
        DropdownMenu(
            expanded = popUpState,
            offset = DpOffset(x = 16.dp, y = 8.dp),
            onDismissRequest = { popUpState = false },
            modifier = Modifier
                .width(itemWidth)
                .crop(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            models.map {
                Box(
                    modifier = Modifier
                        .background(DropdownMenuColor)
                        .clickable {
                            onItemClick(it)
                            popUpState = false
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 8.dp)) {
                    Text(
                        text = it,
                        color = DropdownTextColor,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ModelSettingScreenPreview() {
    ModelSettingScreen()
}

