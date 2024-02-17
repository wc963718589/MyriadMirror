package com.example.myriadmirror.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.model.RoleData
import com.example.myriadmirror.util.Constants
import com.example.myriadmirror.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository,
): ViewModel() {
    val roleId: Int = savedStateHandle.get<Int>(Screen.roleId)!!

    var roleDetailUiState by mutableStateOf(RoleDetailUiState())
        private set

    init {
        if (!isCreating()) viewModelScope.launch(Dispatchers.IO) {
            roleDetailUiState = chatRepository.getRoleSteam(roleId)
                .filterNotNull()
                .first()
                .toRoleDetailUiState()
        }
    }

    suspend fun saveRole() {
        if (isCreating())
            chatRepository.insertRole(roleDetailUiState.roleData)
        else
            chatRepository.updateRole(roleDetailUiState.roleData)
    }

    private fun isCreating() = roleId == Constants.CREATE_ROLE_ID

    fun updateUiState(roleData: RoleData) {
        roleDetailUiState = RoleDetailUiState(roleData)
    }
}

fun RoleData.toRoleDetailUiState(): RoleDetailUiState = RoleDetailUiState(roleData = this)

data class RoleDetailUiState(val roleData: RoleData = RoleData(0, "", "", ""))