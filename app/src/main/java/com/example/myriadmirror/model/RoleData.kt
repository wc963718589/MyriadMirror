package com.example.myriadmirror.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/// 角色数据
@Entity(tableName = "roles")
data class RoleData(
    @PrimaryKey(autoGenerate = true)
    val roleId: Int,               // 角色ID
    val avatar : String,           // 头像
    val name: String,              // 名称
    val roleDescription: String    // 角色描述
)
