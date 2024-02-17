package com.example.myriadmirror.ui.view


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.TextColorBlack
import com.example.myriadmirror.ui.theme.TextColorGrey
import com.example.myriadmirror.ui.theme.TextColorRed

@Composable
fun ClearDialog(state: Boolean, onDismiss: () -> Unit = {}, onConfirm: () -> Unit = {}) {
    if (state) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(R.string.clear_messages_title),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = TextColorBlack
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.clear_messages_context),
                    fontSize = 16.sp,
                    color = TextColorBlack
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(
                        text = stringResource(R.string.clear_messages_confirm),
                        fontSize = 14.sp,
                        color = TextColorRed,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.clear_messages_cancel),
                        fontSize = 14.sp,
                        color = TextColorGrey,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun ClearDialogPreview() {
    ClearDialog(true)
}

