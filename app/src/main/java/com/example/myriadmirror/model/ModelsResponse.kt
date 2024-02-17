package com.example.myriadmirror.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/// 可以与API一起使用的Open AI模型
@Serializable
data class ModelsResponse(
    @SerialName("data")
    val data: List<Model>,
) : BaseResponse() {
    @Serializable
    data class Model(
        @SerialName("owned_by")
        val ownedBy: String         // 拥有该模型的组织
    ) : BaseResponse()
}
