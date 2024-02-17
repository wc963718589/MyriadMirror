package com.example.myriadmirror.database

import androidx.paging.PagingSource
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

    suspend fun insertAllRoles(roles: List<RoleData>)

    /// ChatItem
    fun getAllChatItemsStream(): Flow<List<ChatItemAndRole>>

    fun getChatItemSteam(chatItemId: Int): Flow<ChatItemAndRole?>

    suspend fun insertChatItem(chatItem: ChatItemData)

    suspend fun deleteChatItem(chatItem: ChatItemData)

    suspend fun updateChatItem(chatItem: ChatItemData)

    suspend fun getChatItemIdByRoleId(roleId: Int): Int?

    /// ChatMessage
    fun getAllChatMessagesStream(roleId: Int): PagingSource<Int, ChatMessageWithRole>

    suspend fun getContextTokens(roleId: Int, tokenSize: Int): List<ChatMessageData>

    fun getSingleChatMessageSteam(messageId: Int): Flow<ChatMessageWithRole?>

    suspend fun insertChatMessage(chatMessage: ChatMessageData)

    suspend fun deleteChatMessage(chatMessageWithRole: ChatMessageWithRole, isLast: Boolean = false)

    suspend fun deleteChatMessageByRoleId(roleId: Int)

    suspend fun updateChatMessage(chatMessage: ChatMessageData)
}