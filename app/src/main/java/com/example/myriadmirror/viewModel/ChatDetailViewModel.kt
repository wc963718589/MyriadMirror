package com.example.myriadmirror.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.util.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ChatDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository,
) : ViewModel() {
    val roleId: Int = savedStateHandle.get<Int>(Screen.roleId)!!

    var roleDetailUiState: StateFlow<RoleDetailUiState> =
        chatRepository.getRoleSteam(roleId)
            .map {
                if (it == null)
                    RoleDetailUiState()
                else
                    RoleDetailUiState(it)
            }
            .filterNotNull()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = RoleDetailUiState()
            )
}