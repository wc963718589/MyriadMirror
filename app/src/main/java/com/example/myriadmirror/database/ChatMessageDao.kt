package com.example.myriadmirror.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myriadmirror.model.ChatMessageData
import com.example.myriadmirror.model.ChatMessageWithRole
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(chatMessage: ChatMessageData)

    @Update
    suspend fun update(chatMessage: ChatMessageData)

    @Delete
    suspend fun delete(chatMessage: ChatMessageData)

    @Transaction
    @Query("SELECT * from chatMessages WHERE messageId = :messageId")
    fun getSingleChatMessage(messageId: Int): Flow<ChatMessageWithRole>

    @Transaction
    @Query("SELECT * " +
            "from chatMessages " +
            "WHERE roleId = :roleId " +
            "ORDER BY messageId DESC ")
    fun getAllChatMessages(roleId: Int): PagingSource<Int, ChatMessageWithRole>

    @Query("SELECT * " +
            "from chatMessages " +
            "WHERE roleId = :roleId " +
            "AND isError = 0 " +
            "ORDER BY messageId DESC " +
            "LIMIT :tokenSize")
    suspend fun getContextTokens(roleId: Int, tokenSize: Int): List<ChatMessageData>

    @Query("DELETE FROM chatMessages WHERE roleId = :roleId")
    suspend fun deleteAllByRoleId(roleId: Int)
}