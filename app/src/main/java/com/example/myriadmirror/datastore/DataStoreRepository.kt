package com.example.myriadmirror.datastore

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {
    suspend fun saveApiSetting(baseUrl: String, apiKey: String) =
        dataStoreManager.saveApiSetting(baseUrl, apiKey)

    suspend fun saveInitRoleFlag() = dataStoreManager.saveInitRoleFlag()

    suspend fun saveModel(model: String) = dataStoreManager.saveModel(model)

    suspend fun saveModelSetting(model: String, temperature: Double, topP: Double) =
        dataStoreManager.saveModelSetting(model, temperature, topP)

    suspend fun initConfig() = dataStoreManager.initConfig()

    val initRoleFlag: Flow<Boolean> = dataStoreManager.initRoleFlag
}
