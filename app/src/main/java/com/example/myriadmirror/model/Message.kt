package com.example.myriadmirror.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Serializable
data class Message(
    @SerialName("role")
    var role: String,          // 消息发送者角色
    @SerialName("content")
    var content: String        // 消息内容
) {
    fun toMap(): MutableMap<String, String> {
        return mutableMapOf(
            "role" to role,
            "content" to content
        )
    }
}

fun MutableList<Message>.toListMap(): MutableList<MutableMap<String, String>> {
    return this.mapTo(mutableListOf()) { message ->
        message.toMap()
    }
}

fun MutableList<Message>.toSaveToken(): String {
    return Json.encodeToString(ListSerializer(Message.serializer()), this)
}