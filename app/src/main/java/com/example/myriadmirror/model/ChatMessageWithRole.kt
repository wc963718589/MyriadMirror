package com.example.myriadmirror.model

import androidx.room.Embedded
import androidx.room.Relation

data class ChatMessageWithRole(
    @Embedded
    val chatMessage: ChatMessageData,
    @Relation(
        parentColumn = "roleId",
        entityColumn = "roleId"
    )
    val role: RoleData
)
