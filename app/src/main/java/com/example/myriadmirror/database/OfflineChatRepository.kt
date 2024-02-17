package com.example.myriadmirror.database

import androidx.paging.PagingSource
import com.example.myriadmirror.model.ChatItemAndRole
import com.example.myriadmirror.model.ChatItemData
import com.example.myriadmirror.model.ChatMessageData
import com.example.myriadmirror.model.ChatMessageWithRole
import com.example.myriadmirror.model.RoleData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

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

    override suspend fun insertAllRoles(roles: List<RoleData>) = roleDao.insertAll(roles)

    /// ChatItem
    override fun getAllChatItemsStream(): Flow<List<ChatItemAndRole>> =
        chatItemDao.getAllChatItems()

    override fun getChatItemSteam(chatItemId: Int): Flow<ChatItemAndRole?> =
        chatItemDao.getChatItem(chatItemId)

    override suspend fun insertChatItem(chatItem: ChatItemData) =
        chatItemDao.insert(chatItem)

    override suspend fun deleteChatItem(chatItem: ChatItemData) =
        chatItemDao.delete(chatItem)

    override suspend fun updateChatItem(chatItem: ChatItemData) =
        chatItemDao.update(chatItem)

    override suspend fun getChatItemIdByRoleId(roleId: Int) =
        chatItemDao.getChatItemIdByRoleId(roleId)

    /// ChatMessage
    override fun getAllChatMessagesStream(
        roleId: Int
    ): PagingSource<Int, ChatMessageWithRole> = chatMessageDao.getAllChatMessages(roleId)

    override suspend fun getContextTokens(roleId: Int, tokenSize: Int): List<ChatMessageData> =
        chatMessageDao.getContextTokens(roleId, tokenSize)

    override fun getSingleChatMessageSteam(messageId: Int): Flow<ChatMessageWithRole?> =
        chatMessageDao.getSingleChatMessage(messageId)

    override suspend fun insertChatMessage(chatMessage: ChatMessageData) {
        chatMessageDao.insert(chatMessage)
        chatItemDao.updateLastByRoleId(
            roleId = chatMessage.roleId,
            lastContent = chatMessage.content,
            lastTime = chatMessage.time
        )
    }

    override suspend fun deleteChatMessage(chatMessageWithRole: ChatMessageWithRole, isLast: Boolean) {
        chatMessageDao.delete(chatMessageWithRole.chatMessage)
        if (isLast) {
            chatItemDao.updateLastByRoleId(
                roleId = chatMessageWithRole.role.roleId,
                lastContent = "",
                lastTime = LocalDateTime.now()
            )
        }
    }

    override suspend fun deleteChatMessageByRoleId(roleId: Int) {
        chatMessageDao.deleteAllByRoleId(roleId)
        chatItemDao.updateLastByRoleId(
            roleId = roleId,
            lastContent = "",
            lastTime = LocalDateTime.now()
        )
    }

    override suspend fun updateChatMessage(chatMessage: ChatMessageData) =
        chatMessageDao.update(chatMessage)
}