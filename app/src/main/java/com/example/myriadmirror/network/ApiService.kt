package com.example.myriadmirror.network

import com.example.myriadmirror.model.ChatResponse
import com.example.myriadmirror.model.ModelsResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    /// get
    @GET("v1/models")
    fun getModels(): Call<ModelsResponse>

    /// post
    @POST("v1/chat/completions")
    fun chatCompletions(
        @Body request: RequestBody
    ) : Call<ChatResponse>
}