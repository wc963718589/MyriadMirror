package com.example.myriadmirror.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.model.RoleData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RoleListViewModel(
    private val chatRepository: ChatRepository
): ViewModel() {
    val roleListUiState: StateFlow<RoleListUiState> =
        chatRepository.getAllRolesStream()
            .map { RoleListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = RoleListUiState()
            )

    suspend fun deleteRole(roleData: RoleData) {
        chatRepository.deleteRole(roleData)
    }
}

data class RoleListUiState(val roleList: List<RoleData> = listOf())
