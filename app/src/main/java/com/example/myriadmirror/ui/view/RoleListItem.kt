package com.example.myriadmirror.ui.view


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.R
import com.example.myriadmirror.model.RoleData
import com.example.myriadmirror.ui.theme.TextColorBlack
import com.example.myriadmirror.ui.theme.TextColorGrey

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoleListItem(
    data: RoleData,
    onItemClick: () -> Unit = {},
    popupItems: @Composable () -> Unit = {}
) {
    PopUpMenuContainer(
        onClick = onItemClick,
        popupItems = popupItems
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(12.dp)
        ) {
            AvatarImage(
                model = data.avatar)
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = data.name.ifEmpty { stringResource(R.string.empty_name_default) },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 1.5.em,
                    color = TextColorBlack
                )
                Text(
                    text = data.roleDescription,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    lineHeight = 2.em,
                    color = TextColorGrey,
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun RoleListItemPreview() {
    RoleListItem(
        RoleData(
            roleId = 1,
            avatar = "https://pub-45c0b529c25a4d388dfa7cf57f35f8f0.r2.dev/avatar/xhs.webp",
            name = "永雏塔菲",
            roleDescription = "你是一个热水器皮套人"
        )
    )
}

