package com.example.myriadmirror.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myriadmirror.R
import com.example.myriadmirror.ui.view.DataEmptyHint
import com.example.myriadmirror.ui.view.RoleListItem
import com.example.myriadmirror.ui.view.SingleDeleteDropdownMenu
import com.example.myriadmirror.viewModel.RoleListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun RoleListScreen(
    onRoleClick: (id: Int) -> Unit = {},
    viewModel: RoleListViewModel = hiltViewModel()
) {
    val roleListUiState by viewModel.roleListUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        if (roleListUiState.roleList.isEmpty())
            DataEmptyHint(stringResource(R.string.role_list_empty_hint))
        else
            LazyColumn {
                items(roleListUiState.roleList) { data ->
                    RoleListItem(
                        data = data,
                        onItemClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                viewModel.createChatItem(data.roleId)
                            }
                            onRoleClick(data.roleId)
                        },
                    ) {
                        SingleDeleteDropdownMenu(
                            onDeleteClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    viewModel.deleteRole(data)
                                }
                            }
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
