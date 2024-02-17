package com.example.myriadmirror.ui.view


import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.AppBarColor
import com.example.myriadmirror.ui.theme.AppBarTitleColor
import com.example.myriadmirror.ui.theme.NormalIconColor
import com.example.myriadmirror.ui.theme.HaloColor
import com.example.myriadmirror.util.clickableWithoutRipple

@Composable
fun CommonAppBar(
    onBackClick: (() -> Unit)? = null,
    actionIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .background(color = AppBarColor)
            .fillMaxWidth()
            .halo()
            .statusBarsPadding()
    ) {
        if (onBackClick != null || actionIcon != null)
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = stringResource(R.string.back),
                    tint = if (onBackClick != null) NormalIconColor else Color.Transparent,
                    modifier = Modifier
                        .clickableWithoutRipple {
                            onBackClick?.invoke()
                        }
                        .padding(8.dp)
                )
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    content()
                }
                if (actionIcon != null) actionIcon()
            }
        else content()
    }
}

fun Modifier.halo(
    radius: Dp = 100.dp,
    blurRadius: Dp = 48.dp,
    color: Color = HaloColor,
    offset: Offset = Offset(24f, 10f)
) = this.then(
    Modifier.drawBehind {
        drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.maskFilter =
                BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
            frameworkPaint.color = color.toArgb()
            it.drawCircle(
                radius = radius.toPx(),
                center = offset,
                paint = paint
            )
        }
    })

@Preview
@Composable
private fun CommonAppBarPreview() {
    CommonAppBar {
        Text(
            text = "聊天",
            color = AppBarTitleColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 20.dp)
        )
    }
}
