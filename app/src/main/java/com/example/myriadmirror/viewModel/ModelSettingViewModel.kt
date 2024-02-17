package com.example.myriadmirror.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myriadmirror.datastore.DataStoreRepository
import com.example.myriadmirror.model.ChatConfig
import com.example.myriadmirror.network.NetworkUtil
import com.example.myriadmirror.network.NetworkUtil.executeWithRetry
import com.example.myriadmirror.util.Constants
import com.example.myriadmirror.util.ToastUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class ModelSettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    var modelSettingUiState: MutableStateFlow<ModelSettingUiState> = MutableStateFlow(
        ModelSettingUiState(
            ChatConfig.model, ChatConfig.temperature, ChatConfig.topP
        )
    )
    var inputUiState: MutableStateFlow<InputUiState> = MutableStateFlow(
        InputUiState(
            transToString(ChatConfig.temperature), transToString(ChatConfig.topP)
        )
    )
    val modelsList = mutableListOf<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getModels()
        }
    }

    private suspend fun getModels() {
        NetworkUtil.api.getModels().executeWithRetry().onSuccess {
                modelsList.clear()
                modelsList.addAll(it.data.map { model -> model.id })
            }.onFailure {
                ToastUtil.show("获取Model列表失败")
            }
    }

    suspend fun saveSetting() {
        dataStoreRepository.saveModelSetting(
            modelSettingUiState.value.model,
            modelSettingUiState.value.temperature,
            modelSettingUiState.value.topP
        )
    }

    fun updateModel(model: String) {
        modelSettingUiState.update {
            it.copy(model = model)
        }
    }

    fun updateSliderTemperature(temperature: Double, updateInput: Boolean = true) {
        modelSettingUiState.update {
            it.copy(temperature = temperature)
        }
        if (updateInput) {
            updateInputTemperature(temperature.toString(), false)
        }
    }

    fun updateSliderTopP(topP: Double, updateInput: Boolean = true) {
        modelSettingUiState.update {
            it.copy(topP = topP)
        }
        if (updateInput) {
            updateInputTopP(topP.toString(), false)
        }
    }

    fun updateInputTemperature(temperature: String, updateSlider: Boolean = true) {
        if (temperature.isEmpty() || temperature.endsWith(".")) {
            inputUiState.update {
                it.copy(temperature = temperature)
            }
            return
        }
        val newValue = transToDouble(temperature) ?: return
        val newInput = transToString(newValue)
        inputUiState.update {
            it.copy(temperature = newInput)
        }
        if (Constants.SETTING_VALUES.contains(newValue) && updateSlider) {
            updateSliderTemperature(newValue, false)
        }
    }

    fun updateInputTopP(topP: String, updateSlider: Boolean = true) {
        if (topP.isEmpty() || topP.endsWith(".")) {
            inputUiState.update {
                it.copy(topP = topP)
            }
            return
        }
        val newValue = transToDouble(topP) ?: return
        val newInput = transToString(newValue)
        inputUiState.update {
            it.copy(topP = newInput)
        }
        if (Constants.SETTING_VALUES.contains(newValue) && updateSlider) {
            updateSliderTopP(newValue, false)
        }
    }

    companion object {
        private fun transToDouble(originInput: String): Double? {
            val tempValue = originInput.toDoubleOrNull() ?: return null
            val newValue = if (tempValue < 0) {
                0.0
            } else if (tempValue > 1) {
                1.0
            } else {
                BigDecimal(tempValue).setScale(2, RoundingMode.HALF_UP).toDouble()
            }
            return newValue
        }

        private fun transToString(newValue: Double): String {
            return when (newValue) {
                0.0 -> {
                    "0"
                }

                1.0 -> {
                    "1"
                }

                else -> newValue.toString()
            }
        }
    }
}

data class ModelSettingUiState(
    val model: String, val temperature: Double, val topP: Double
)

data class InputUiState(
    val temperature: String, val topP: String
)