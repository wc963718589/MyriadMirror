package com.example.myriadmirror.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.ui.theme.ActiveColor
import com.example.myriadmirror.ui.theme.SliderActiveTrackColor
import com.example.myriadmirror.ui.theme.SliderCalibrationColor
import com.example.myriadmirror.ui.theme.SliderInactiveTrackColor
import com.example.myriadmirror.ui.theme.TextColorBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> TitleSettingSlider(
    title: String,
    value: T,
    items: List<T>,
    calibrations: List<T> = listOf(),
    onSliderValueChange: (T) -> Unit,
    enableInput: Boolean = false,
    textFieldValue: String = "",
    onTextFieldValueChange: (String) -> Unit = {},
    hintUnderSlider: @Composable (() -> Unit)? = null
) {
    val inputWidth = 70.dp
    val inputPaddingStart = 8.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = TextColorBlack,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) {
                SliderItem(
                    value = value,
                    items = items,
                    calibrations = calibrations,
                    onValueChange = onSliderValueChange
                )
            }
            if (enableInput) {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = {
                        onTextFieldValueChange(it)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = TextColorBlack,
                        cursorColor = ActiveColor,
                        focusedBorderColor = ActiveColor,
                        focusedLabelColor = ActiveColor
                    ),
                    textStyle = TextStyle(
                        fontSize = 14.sp
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .padding(start = inputPaddingStart)
                        .width(inputWidth)
                        .height(50.dp)
                )
            }
        }
        if (hintUnderSlider != null) {
            Box(modifier = Modifier.padding(top = 2.dp, end = inputWidth + inputPaddingStart)) {
                hintUnderSlider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> SliderItem(
    value: T,
    items: List<T>,
    calibrations: List<T>,
    onValueChange: (T) -> Unit
) {
    val steps = items.size
    val maxRange = items.size.toFloat() - 1
    val haveCalibrations = calibrations.isNotEmpty()
    Box {
        // 背景层
        calibrations.forEachIndexed { index, calibration ->
            Slider(
                value = items.indexOf(calibration).toFloat(),
                onValueChange = {},
                valueRange = 0f..maxRange,
                thumb = {
                    Box(
                        modifier = Modifier
                            .height(26.dp)
                            .width(20.dp)
                            .padding(horizontal = 8.5.dp)
                            .background(SliderCalibrationColor)
                    )
                },
                colors = SliderDefaults.colors(
                    inactiveTrackColor = if (index == 0) Color.Transparent else SliderInactiveTrackColor,
                    inactiveTickColor = Color.Transparent,
                    activeTrackColor = if (index == 0) Color.Transparent else SliderInactiveTrackColor,
                    activeTickColor = Color.Transparent
                )
            )
        }
        // 拖动层
        Slider(
            value = items.indexOf(value).toFloat(),
            onValueChange = {
                onValueChange(items[it.toInt()])
            },
            valueRange = 0f..maxRange,
            steps = steps,
            colors = SliderDefaults.colors(
                inactiveTrackColor = if (haveCalibrations) Color.Transparent else SliderInactiveTrackColor,
                inactiveTickColor = Color.Transparent,
                activeTrackColor = SliderActiveTrackColor,
                activeTickColor = Color.Transparent
            )
        )
    }

}

@Preview
@Composable
fun TitleSliderPreview() {
    //TitleSlider()
}
