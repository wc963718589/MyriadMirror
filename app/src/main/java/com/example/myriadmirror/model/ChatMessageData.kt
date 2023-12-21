package com.example.myriadmirror.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/// 单个聊天信息数据
@Entity(
    tableName = "chatMessages",
    foreignKeys = [ForeignKey(
        entity = RoleData::class,
        parentColumns = arrayOf("roleId"),
        childColumns = arrayOf("roleId"),
        onDelete = ForeignKey.Companion.CASCADE
    )],
    indices = [Index("roleId")]
)
data class ChatMessageData(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int,     // 该聊天ID
    val roleId: Int,        // 聊天对象ID
    val content: String,    // 内容
    val time: String,       // 发送时间
    val isSend: Boolean     // 是否为本人发送
)
