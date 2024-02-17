package com.example.myriadmirror.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.model.ChatItemData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ChatItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(chatItem: ChatItemData)

    @Update
    suspend fun update(chatItem: ChatItemData)

    @Delete
    suspend fun delete(chatItem: ChatItemData)

    @Transaction
    @Query("SELECT * from chatItems WHERE chatItemId = :chatItemId")
    fun getChatItem(chatItemId: Int): Flow<ChatItemAndRole>

    @Transaction
    @Query("SELECT * from chatItems ORDER BY lastTime DESC")
    fun getAllChatItems(): Flow<List<ChatItemAndRole>>

    @Query("SELECT chatItemId from chatItems WHERE roleId = :roleId")
    fun getChatItemIdByRoleId(roleId: Int): Int?

    @Query("UPDATE chatItems " +
            "SET lastContent = :lastContent , " +
            "lastTime = :lastTime " +
            "WHERE roleId = :roleId")
    suspend fun updateLastByRoleId(roleId: Int, lastContent: String, lastTime: LocalDateTime)
}