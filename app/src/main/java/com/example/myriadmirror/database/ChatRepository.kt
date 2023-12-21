package com.example.myriadmirror.database

import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.model.ChatItemData
import com.example.myriadmirror.model.ChatMessageData
import com.example.myriadmirror.model.ChatMessageWithRole
import com.example.myriadmirror.model.RoleData
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    /// Role
    fun getAllRolesStream(): Flow<List<RoleData>>

    fun getRoleSteam(roleId: Int): Flow<RoleData?>

    suspend fun insertRole(role: RoleData)

    suspend fun deleteRole(role: RoleData)

    suspend fun updateRole(role: RoleData)

    /// ChatItem
    fun getAllChatItemsStream(): Flow<List<ChatItemAndRole>>

    fun getChatItemSteam(chatItemId: Int): Flow<ChatItemAndRole?>

    suspend fun insertChatItem(chatItemAndRole: ChatItemAndRole)

    suspend fun deleteChatItem(chatItemAndRole: ChatItemAndRole)

    suspend fun updateChatItem(chatItemAndRole: ChatItemAndRole)

    /// ChatMessage
    fun getChatMessagesStream(roleId: Int, limit: Int, offset: Int): Flow<List<ChatMessageWithRole>>

    fun getSingleChatMessageSteam(messageId: Int): Flow<ChatMessageWithRole?>

    suspend fun insertChatMessage(chatMessageWithRole: ChatMessageWithRole)

    suspend fun deleteChatMessage(chatMessageWithRole: ChatMessageWithRole)

    suspend fun updateChatMessage(chatMessageWithRole: ChatMessageWithRole)
}