package com.example.myriadmirror

import android.app.Application
import com.example.myriadmirror.util.AppContainer
import com.example.myriadmirror.util.AppDataContainer

class MyriadMirrorApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}