package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import com.example.myriadmirror.ui.theme.ActiveColor
import com.example.myriadmirror.ui.theme.AppBarTitleColor
import com.example.myriadmirror.ui.theme.NormalBackgroundColor
import com.example.myriadmirror.ui.theme.NormalIconColor
import com.example.myriadmirror.ui.theme.TextColorBlack
import com.example.myriadmirror.ui.theme.TextColorGrey
import com.example.myriadmirror.ui.view.CommonAppBar
import com.example.myriadmirror.util.clickableWithoutRipple
import com.example.myriadmirror.viewModel.RoleDetailViewModel
import com.example.myriadmirror.viewModel.ViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleDetailScreen(
    navigationUp: () -> Unit = {},
    viewModel: RoleDetailViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CommonAppBar(
                onBackClick = navigationUp,
                actionIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_confirm),
                        contentDescription = stringResource(R.string.confirm_role_setting),
                        tint = NormalIconColor,
                        modifier = Modifier
                            .clickableWithoutRipple {
                                coroutineScope.launch {
                                    viewModel.saveRole()
                                    navigationUp.invoke()
                                }
                            }
                            .padding(8.dp)
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.role_detail_title),
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
        Column(modifier = Modifier
            .fillMaxSize()
            .background(NormalBackgroundColor)
            .padding(it)) {
            RoleTextField(
                value = viewModel.roleDetailUiState.roleData.name,
                label = stringResource(R.string.set_role_name_title),
                placeholder = stringResource(R.string.set_role_name_hint),
            ) { value ->
                viewModel.updateUiState(viewModel.roleDetailUiState.roleData.copy(name = value))
            }
            RoleTextField(
                value = viewModel.roleDetailUiState.roleData.avatar,
                label = stringResource(R.string.set_role_avatar_title),
                placeholder = stringResource(R.string.set_role_avatar_hint),
            ) { value ->
                viewModel.updateUiState(viewModel.roleDetailUiState.roleData.copy(avatar = value))
            }
            RoleTextField(
                value = viewModel.roleDetailUiState.roleData.roleDescription,
                label = stringResource(R.string.set_role_description_title),
                placeholder = stringResource(R.string.set_role_description_hint),
            ) { value ->
                viewModel.updateUiState(viewModel.roleDetailUiState.roleData.copy(roleDescription = value))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoleTextField(
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
                fontSize = 16.sp, color = TextColorGrey)
        },
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

@Preview
@Composable
fun CreateRoleScreenPreview() {
    RoleDetailScreen()
}

