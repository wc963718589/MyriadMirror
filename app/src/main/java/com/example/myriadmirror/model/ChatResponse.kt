package com.example.myriadmirror.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    @SerialName("model")
    val model: String,                          // 用于聊天完成的模型
    @SerialName("system_fingerprint")
    val systemFingerprint: String?,             // 该指纹表示模型运行的后端配置
    @SerialName("choices")
    val choices: List<Choice>,                  // 聊天完成选项列表。如果n大于1,可以有多个选项
    @SerialName("usage")
    val usage: Usage,                           // 完成请求的使用统计信息
) : BaseResponse() {
    @Serializable
    data class Choice(
        @SerialName("index")
        val index: Int,
        @SerialName("finish_reason")
        val finishReason: String,               // 模型停止原因
        @SerialName("message")
        val message: Message,                   // 完成请求的使用统计信息
        @SerialName("logprobs")
        val logprobs: Boolean?,
    )

    @Serializable
    data class Usage(
        @SerialName("completion_tokens")
        val completionTokens: Int,              // 生成的完成中的标记数
        @SerialName("prompt_tokens")
        val promptTokens: Int,                  // 提示中的标记数
        @SerialName("total_tokens")
        val totalTokens: Int,                   // 请求中使用的标记总数(提示 + 完成)
    )
}
