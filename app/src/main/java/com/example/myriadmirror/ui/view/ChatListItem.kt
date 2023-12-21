package com.example.myriadmirror.ui.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.ui.theme.TextColorBlack
import com.example.myriadmirror.ui.theme.TextColorGrey


@Composable
fun ChatListItem(
    data: ChatItemAndRole,
    onItemClick: (roleId: Int) -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
              onItemClick(data.role.roleId)
            }
            .padding(12.dp)
    ) {
        AvatarImage(
            model = data.role.avatar,
            size = 56.dp
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Row {
                Text(
                    text = data.role.name.ifEmpty { stringResource(R.string.empty_name_default) },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 1.5.em,
                    color = TextColorBlack,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp))
                Text(
                    text = data.chatItem.lastTime.toString(),// TODO: DateFormat
                    fontSize = 14.sp,
                    lineHeight = 1.5.em,
                    color = TextColorGrey)
            }
            Text(text = data.chatItem.lastContent,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                lineHeight = 2.em,
                color = TextColorGrey,
                modifier = Modifier
                    .padding(top = 4.dp))
        }
    }
}

@Preview
@Composable
private fun ChatListItemPreview() {
//    ChatListItem()
}
