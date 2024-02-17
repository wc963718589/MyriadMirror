package com.example.myriadmirror.ui.view


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myriadmirror.ui.theme.ActiveColor
import com.example.myriadmirror.ui.theme.TextColorBlack
import com.example.myriadmirror.ui.theme.TextColorGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTextField(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String)-> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label,
                fontSize = 16.sp)
        },
        placeholder = {
            Text(text = placeholder,
                fontSize = 16.sp, color = TextColorGrey
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 1.5.em
        ),
        maxLines = 4,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = TextColorBlack,
            cursorColor = ActiveColor,
            focusedBorderColor = ActiveColor,
            focusedLabelColor = ActiveColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    )
}
