package com.example.myriadmirror.viewModel

import androidx.lifecycle.ViewModel
import com.example.myriadmirror.datastore.DataStoreRepository
import com.example.myriadmirror.model.ChatConfig
import com.example.myriadmirror.model.toListMap
import com.example.myriadmirror.network.NetConfig
import com.example.myriadmirror.network.NetworkUtil
import com.example.myriadmirror.network.NetworkUtil.executeWithRetry
import com.example.myriadmirror.network.chatCompletionsBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ApiKeySettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    var apiKeySettingUiState: MutableStateFlow<ApiKeySettingUiState> =
        MutableStateFlow(ApiKeySettingUiState())

    suspend fun saveSetting() {
        dataStoreRepository.saveApiSetting(
            apiKeySettingUiState.value.baseUrl,
            apiKeySettingUiState.value.apiKey
        )
        getDefaultModel()
    }

    private suspend fun getDefaultModel() {
        NetworkUtil.api.getModels()
            .executeWithRetry()
            .onSuccess {
                dataStoreRepository.saveModel(it.data.first().id)
            }
    }

    fun updateUrl(baseUrl: String) {
        apiKeySettingUiState.update {
            it.copy(baseUrl = baseUrl)
        }
    }

    fun updateKey(apiKey: String) {
        apiKeySettingUiState.update {
            it.copy(apiKey = apiKey)
        }
    }
}

data class ApiKeySettingUiState(
    val baseUrl: String = NetConfig.baseUrl,
    val apiKey: String = NetConfig.apiKey
)