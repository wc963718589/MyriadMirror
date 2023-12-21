package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.theme.NormalBackgroundColor
import com.example.myriadmirror.ui.view.DataEmptyHint
import com.example.myriadmirror.ui.view.RoleListItem
import com.example.myriadmirror.viewModel.RoleListViewModel
import com.example.myriadmirror.viewModel.ViewModelProvider
import kotlinx.coroutines.launch


@Composable
fun RoleListScreen(
    onRoleClick: (id: Int) -> Unit = {},
    viewModel: RoleListViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val roleListUiState by viewModel.roleListUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Surface(color = NormalBackgroundColor, modifier = Modifier.fillMaxSize()) {
        if (roleListUiState.roleList.isEmpty())
            DataEmptyHint(stringResource(R.string.role_list_empty_hint))
        else
            LazyColumn {
                items(roleListUiState.roleList) { data ->
                    RoleListItem(
                        data = data,
                        onItemClick = onRoleClick,
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.delete),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth())
                            }, onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteRole(it)
                                }
                            },
                            contentPadding = PaddingValues()
                        )
                    }
                }
            }
    }
}

@Preview
@Composable
private fun RoleListScreenPreview() {
    RoleListScreen()
}
