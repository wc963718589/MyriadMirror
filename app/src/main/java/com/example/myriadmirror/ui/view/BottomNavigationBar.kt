package com.example.myriadmirror.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.ui.theme.ActiveColor
import com.example.myriadmirror.ui.theme.BottomNavigationBackgroundColor
import com.example.myriadmirror.ui.theme.InactiveColor
import com.example.myriadmirror.ui.theme.ShadowColor
import com.example.myriadmirror.util.clickableWithoutRipple
import com.example.myriadmirror.util.customShadow

@Composable
fun BottomNavigationBar(items: List<Pair<Int, Int>>, value: Int, onValueChange: (Int) -> Unit) {
    Surface(shadowElevation = 10.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BottomNavigationBackgroundColor)
                .navigationBarsPadding()
            , horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = item.second,
                    title = stringResource(item.first),
                    isSelected = value == index
                ) {
                    onValueChange(index)
                }
            }
        }
    }
}

@Composable
private fun NavigationBarItem(
    @DrawableRes icon: Int, title: String, isSelected: Boolean, onClick: () -> Unit
) {
    val color = if (isSelected) ActiveColor else InactiveColor
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickableWithoutRipple {
                onClick.invoke()
            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = title, tint = color
        )
        Text(
            text = title, modifier = Modifier.padding(top = 4.dp), fontSize = 12.sp, color = color
        )
    }
}
