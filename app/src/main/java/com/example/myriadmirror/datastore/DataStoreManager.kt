package com.example.myriadmirror.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myriadmirror.model.ChatConfig
import com.example.myriadmirror.network.NetConfig
import com.example.myriadmirror.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

    private val baseUrlKey = stringPreferencesKey(BASE_URL_KEY)
    private val apiKeyKey = stringPreferencesKey(API_KEY_KEY)
    private val gptModelKey = stringPreferencesKey(GPT_MODEL_KEY)
    private val temperatureKey = doublePreferencesKey(TEMPERATURE_KEY)
    private val topPKey = doublePreferencesKey(TOP_P_KEY)
    private val initRoleFlagKey = booleanPreferencesKey(INIT_ROLE_FLAG)

    suspend fun saveApiSetting(baseUrl: String, apiKey: String) {
        context.dataStore.edit {
            it[baseUrlKey] = baseUrl
            it[apiKeyKey] = apiKey
        }
    }

    suspend fun saveInitRoleFlag() {
        context.dataStore.edit {
            it[initRoleFlagKey] = true
        }
    }

    suspend fun saveModel(model: String) {
        context.dataStore.edit {
            it[gptModelKey] = model
        }
    }

    suspend fun saveModelSetting(model: String, temperature: Double, topP: Double) {
        context.dataStore.edit {
            it[gptModelKey] = model
            it[temperatureKey] = temperature
            it[topPKey] = topP
        }
    }

    suspend fun initConfig() = context.dataStore.data
        .collect {
            NetConfig.apiKey = it[apiKeyKey] ?: ""
            NetConfig.baseUrl = if (it[baseUrlKey].isNullOrEmpty()) Constants.DEFAULT_BASEURL else it[baseUrlKey]!!
            ChatConfig.model = it[gptModelKey] ?: ""
            ChatConfig.temperature = it[temperatureKey] ?: Constants.DEFAULT_TEMPERATURE
            ChatConfig.topP = it[topPKey] ?: Constants.DEFAULT_TOP_P
        }

    val initRoleFlag: Flow<Boolean> = context.dataStore.data
        .map { it[initRoleFlagKey] ?: false }

    companion object {
        const val DATA_STORE_NAME = "myriad_mirror_data"
        const val BASE_URL_KEY = "base_url"
        const val API_KEY_KEY = "api_key"
        const val GPT_MODEL_KEY = "gpt_model"
        const val TEMPERATURE_KEY = "temperature"
        const val TOP_P_KEY = "top_p"
        const val INIT_ROLE_FLAG = "init_role_flag"
    }
}