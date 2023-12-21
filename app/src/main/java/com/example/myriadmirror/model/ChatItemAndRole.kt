package com.example.myriadmirror.model

import androidx.room.Embedded
import androidx.room.Relation

data class ChatItemAndRole(
    @Embedded
    val chatItem: ChatItemData,
    @Relation(
        parentColumn = "roleId",
        entityColumn = "roleId"
    )
    val role: RoleData
)
