package com.example.myriadmirror.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.model.ChatItemData
import com.example.myriadmirror.model.RoleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RoleListViewModel @Inject constructor(
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

    suspend fun createChatItem(roleId: Int) {
        if (chatRepository.getChatItemIdByRoleId(roleId) == null) {
            chatRepository.insertChatItem(
                ChatItemData(
                    roleId = roleId,
                    lastContent = "",
                    lastTime = LocalDateTime.now()
                )
            )
        }
    }
}

data class RoleListUiState(val roleList: List<RoleData> = listOf())
