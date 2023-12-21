package com.example.myriadmirror.database

import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.model.ChatMessageWithRole
import com.example.myriadmirror.model.RoleData
import kotlinx.coroutines.flow.Flow

class OfflineChatRepository(
    private val roleDao: RoleDao,
    private val chatItemDao: ChatItemDao,
    private val chatMessageDao: ChatMessageDao
) : ChatRepository {
    /// Role
    override fun getAllRolesStream(): Flow<List<RoleData>> = roleDao.getAllRoles()

    override fun getRoleSteam(roleId: Int): Flow<RoleData?> = roleDao.getRole(roleId)

    override suspend fun insertRole(role: RoleData) = roleDao.insert(role)

    override suspend fun deleteRole(role: RoleData) = roleDao.delete(role)

    override suspend fun updateRole(role: RoleData) = roleDao.update(role)

    /// ChatItem
    override fun getAllChatItemsStream(): Flow<List<ChatItemAndRole>> =
        chatItemDao.getAllChatItems()

    override fun getChatItemSteam(chatItemId: Int): Flow<ChatItemAndRole?> =
        chatItemDao.getChatItem(chatItemId)

    override suspend fun insertChatItem(chatItemAndRole: ChatItemAndRole) =
        chatItemDao.insert(chatItemAndRole.chatItem)

    override suspend fun deleteChatItem(chatItemAndRole: ChatItemAndRole) =
        chatItemDao.delete(chatItemAndRole.chatItem)

    override suspend fun updateChatItem(chatItemAndRole: ChatItemAndRole) =
        chatItemDao.update(chatItemAndRole.chatItem)

    /// ChatMessage
    override fun getChatMessagesStream(
        roleId: Int,
        limit: Int,
        offset: Int
    ): Flow<List<ChatMessageWithRole>> = chatMessageDao.getChatMessages(roleId, limit, offset)

    override fun getSingleChatMessageSteam(messageId: Int): Flow<ChatMessageWithRole?> =
        chatMessageDao.getSingleChatMessage(messageId)

    override suspend fun insertChatMessage(chatMessageWithRole: ChatMessageWithRole) =
        chatMessageDao.insert(chatMessageWithRole.chatMessage)

    override suspend fun deleteChatMessage(chatMessageWithRole: ChatMessageWithRole) =
        chatMessageDao.delete(chatMessageWithRole.chatMessage)

    override suspend fun updateChatMessage(chatMessageWithRole: ChatMessageWithRole) =
        chatMessageDao.update(chatMessageWithRole.chatMessage)
}