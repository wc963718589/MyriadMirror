package com.example.myriadmirror.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myriadmirror.MyriadMirrorApplication

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel()
        }

        initializer {
            ChatListViewModel(application().container.chatRepository)
        }

        initializer {
            RoleListViewModel(application().container.chatRepository)
        }

        initializer {
            RoleDetailViewModel(
                this.createSavedStateHandle(),
                application().container.chatRepository)
        }

        initializer {
            ChatDetailViewModel(
                this.createSavedStateHandle(),
                application().container.chatRepository)
        }
    }
}

fun CreationExtras.application(): MyriadMirrorApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyriadMirrorApplication)