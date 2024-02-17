package com.example.myriadmirror.util

import android.content.Context
import com.example.myriadmirror.database.ChatRepository
import com.example.myriadmirror.database.MyriadMirrorDatabase
import com.example.myriadmirror.database.OfflineChatRepository
import com.example.myriadmirror.datastore.DataStoreManager
import com.example.myriadmirror.datastore.DataStoreRepository
import com.example.myriadmirror.network.AppendHeaderParamInterceptor
import com.example.myriadmirror.network.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStoreModule(
        @ApplicationContext context: Context
    ) = DataStoreManager(context)

    @Provides
    @Singleton
    fun provideDataRepository(dataStoreManager: DataStoreManager): DataStoreRepository {
        return DataStoreRepository(dataStoreManager)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideChatRepository(@ApplicationContext context: Context): ChatRepository {
        return OfflineChatRepository(
            MyriadMirrorDatabase.getDatabase(context).RoleDao(),
            MyriadMirrorDatabase.getDatabase(context).ChatItemDao(),
            MyriadMirrorDatabase.getDatabase(context).ChatMessageDao())
    }
}
