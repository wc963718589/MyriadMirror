package com.example.myriadmirror.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime

/// 聊天列表数据
@Entity(
    tableName = "chatItems",
    foreignKeys = [ForeignKey(
        entity = RoleData::class,
        parentColumns = arrayOf("roleId"),
        childColumns = arrayOf("roleId"),
        onDelete = CASCADE
    )],
    indices = [Index("roleId")]
)
data class ChatItemData(
    @PrimaryKey(autoGenerate = true)
    val chatItemId: Int = 0,    // 聊天列表ID
    val roleId: Int,            // 聊天对象ID
    val lastContent: String,    // 最后一条内容
    val lastTime: LocalDateTime // 最后一条时间
)
