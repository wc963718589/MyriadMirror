package com.example.myriadmirror

import android.app.Application
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.datastore.DataStoreRepository
import com.example.myriadmirror.model.RoleData
import com.example.myriadmirror.util.DefaultRoles
import com.example.myriadmirror.util.ToastUtil
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class MyriadMirrorApplication: Application() {
    @Inject lateinit var chatRepository: ChatRepository
    @Inject lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate() {
        super.onCreate()
        initRoleData()
        initConfig()
        ToastUtil.init(this)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initRoleData() {
        GlobalScope.launch(Dispatchers.IO) {
            if (dataStoreRepository.initRoleFlag.filterNotNull().first()) {
                return@launch
            }
            chatRepository.insertAllRoles(DefaultRoles.data)
            dataStoreRepository.saveInitRoleFlag()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initConfig() {
        GlobalScope.launch(Dispatchers.IO) {
            dataStoreRepository.initConfig()
        }
    }
}