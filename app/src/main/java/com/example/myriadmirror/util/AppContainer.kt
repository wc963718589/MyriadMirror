package com.example.myriadmirror.util

import android.content.Context
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.database.MyriadMirrorDatabase
import com.example.myriadmirror.database.OfflineChatRepository

interface AppContainer {
    val chatRepository: ChatRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val chatRepository: ChatRepository by lazy {
        OfflineChatRepository(
            MyriadMirrorDatabase.getDatabase(context).RoleDao(),
            MyriadMirrorDatabase.getDatabase(context).ChatItemDao(),
            MyriadMirrorDatabase.getDatabase(context).ChatMessageDao())
    }
}