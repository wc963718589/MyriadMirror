package com.example.myriadmirror.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "roleTokens",
    foreignKeys = [ForeignKey(
        entity = RoleData::class,
        parentColumns = arrayOf("roleId"),
        childColumns = arrayOf("roleId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("roleId")]
)
data class RoleTokens(
    @PrimaryKey(autoGenerate = true)
    val tokenId: Int = 0,       // Token ID
    val roleId: Int,            // 聊天对象ID
    val lastContent: String,    // 最后一条内容
    val lastTime: LocalDateTime // 最后一条时间
)
