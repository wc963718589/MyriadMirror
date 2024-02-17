package com.example.myriadmirror.ui.view


import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.myriadmirror.util.crop

@Composable
fun PopUpMenuContainer(
    onClick: () -> Unit = {},
    onLongPress: () -> Unit = {},
    popupItems: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    var popUpState by remember { mutableStateOf(false) }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current
    Box(modifier = Modifier
        .indication(interactionSource, LocalIndication.current)
        .pointerInput(true) {
            detectTapGestures(
                onTap = { onClick() },
                onLongPress = {
                    pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    onLongPress.invoke()
                    popUpState = true
                },
                onPress = {
                    // 水波纹动效
                    val press = PressInteraction.Press(it)
                    interactionSource.emit(press)
                    tryAwaitRelease()
                    interactionSource.emit(PressInteraction.Release(press))
                }
            )
        }
        .onSizeChanged {
            itemHeight = with(density) { it.height.toDp() }
        }) {
        content()
        DropdownMenu(expanded = popUpState,
            offset = pressOffset.copy(y = pressOffset.y - itemHeight),
            onDismissRequest = { popUpState = false },
            modifier = Modifier.crop(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))) {
            popupItems()
        }
    }
}
