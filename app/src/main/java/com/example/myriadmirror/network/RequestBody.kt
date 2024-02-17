package com.example.myriadmirror.network

import okhttp3.RequestBody

fun chatCompletionsBody(
    model: String,
    messages: MutableList<MutableMap<String, String>>,
    temperature: Double,
    topP: Double?): RequestBody {
    return NetworkUtil.buildRequestBody(
        mutableMapOf(
            "model" to model,
            "messages" to messages,
            "temperature" to temperature,
            "top_p" to topP
        )
    )
}