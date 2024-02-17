package com.example.myriadmirror.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    @SerialName("id")
    val id: String = "",
    @SerialName("object")
    val obj: String = "",           // 对象类型
    @SerialName("created")
    val created: Int = 0,           // 创建的Unix时间戳(秒)
)
