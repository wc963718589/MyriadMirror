package com.example.myriadmirror.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myriadmirror.model.ChatItemData
import com.example.myriadmirror.model.ChatMessageData
import com.example.myriadmirror.model.RoleData
import com.example.myriadmirror.util.Converters

@Database(entities = [RoleData::class, ChatItemData::class, ChatMessageData::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyriadMirrorDatabase: RoomDatabase() {
    abstract fun RoleDao(): RoleDao
    abstract fun ChatItemDao(): ChatItemDao
    abstract fun ChatMessageDao(): ChatMessageDao

    companion object {
        private var Instance: MyriadMirrorDatabase? = null

        fun getDatabase(context: Context): MyriadMirrorDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MyriadMirrorDatabase::class.java, "myriad_mirror_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}