package com.example.myriadmirror.util

import android.app.Application
import android.graphics.BlurMaskFilter
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myriadmirror.MyriadMirrorApplication
import java.time.LocalDateTime

/// 无水波纹点击
fun Modifier.clickableWithoutRipple(
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

/// 裁剪，用于去除弹出框padding
fun Modifier.crop(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
): Modifier = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    fun Dp.toPxInt(): Int = this.toPx().toInt()

    layout(
        placeable.width - (horizontal * 2).toPxInt(),
        placeable.height - (vertical * 2).toPxInt()
    ) {
        placeable.placeRelative(-horizontal.toPx().toInt(), -vertical.toPx().toInt())
    }
}

fun Modifier.customShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.autoCloseKeyboard(keyboardController: SoftwareKeyboardController?): Modifier = this.then(
    Modifier. pointerInput(Unit) {
        detectTapGestures(onTap = {
            keyboardController?.hide()
        })
    }
)

@Composable
fun LocalDateTime.formatDateTime(): String {
    val currentTime = LocalDateTime.now()
    val currentDay = currentTime.toLocalDate()
    val targetDay = this.toLocalDate()

    if (currentDay.isEqual(targetDay)) {
        return this.format(Constants.TODAY_FORMATTER)
    }
    if (currentDay.minusDays(1).isEqual(targetDay)) {
        return this.format(Constants.YESTERDAY_FORMATTER)
    }
    if (currentDay.minusWeeks(1).isBefore(targetDay)) {
        return Constants.IN_WEEK_LIST[this.dayOfWeek.value - 1]
    }
    if (currentDay.minusYears(1).isBefore(targetDay)) {
        return this.format(Constants.IN_YEAR_FORMATTER)
    }
    return this.format(Constants.OUT_YEAR_FORMATTER)
}
