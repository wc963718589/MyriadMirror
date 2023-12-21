package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.AppBarTitleColor
import com.example.myriadmirror.ui.theme.NormalBackgroundColor
import com.example.myriadmirror.ui.theme.NormalIconColor
import com.example.myriadmirror.ui.view.CommonAppBar
import com.example.myriadmirror.util.clickableWithoutRipple
import com.example.myriadmirror.viewModel.ChatDetailViewModel
import com.example.myriadmirror.viewModel.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    onBackClick: () -> Unit = {},
    onOptionClick: (Int) -> Unit = {},
    viewModel: ChatDetailViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val roleDetailUiState by viewModel.roleDetailUiState.collectAsState()
    Scaffold(
        topBar = {
            CommonAppBar(
                onBackClick = onBackClick,
                actionIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_option),
                        contentDescription = stringResource(R.string.set_role),
                        tint = NormalIconColor,
                        modifier = Modifier
                            .clickableWithoutRipple {
                                onOptionClick.invoke(viewModel.roleId)
                            }
                            .padding(8.dp)
                    )
                }
            ) {
                Text(
                    text = roleDetailUiState.roleData.name.ifEmpty { stringResource(R.string.empty_name_default) },
                    color = AppBarTitleColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 20.dp)
                )
            }
        }
    ) {
        LazyColumn(modifier = Modifier
            .background(NormalBackgroundColor)
            .padding(it)) {

        }
    }
}

@Preview
@Composable
fun ChatDetailScreenPreview() {
    ChatDetailScreen()
}

