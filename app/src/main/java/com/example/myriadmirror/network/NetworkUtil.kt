package com.example.myriadmirror.network

import com.example.myriadmirror.datastore.DataStoreRepository
import com.example.myriadmirror.model.BaseResponse
import com.example.myriadmirror.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

object NetworkUtil {
    private const val OUT_TIME = 30L

    val api: ApiService by lazy {
        createRetrofitService(ApiService::class.java)
    }

    private fun <T> createRetrofitService(clazz: Class<T>): T {
        val builder = OkHttpClient.Builder()
            .addInterceptor(AppendHeaderParamInterceptor())
            .readTimeout(OUT_TIME, TimeUnit.SECONDS)
            .connectTimeout(OUT_TIME, TimeUnit.SECONDS)
            .writeTimeout(OUT_TIME, TimeUnit.SECONDS)

        val url = if (NetConfig.baseUrl.endsWith("/")) NetConfig.baseUrl else "${NetConfig.baseUrl}/"

        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(url)
            .client(builder.build())
            .build()
        return retrofit.create(clazz)
    }

    suspend fun <T : BaseResponse> Call<T>.executeWithRetry(
        retryCount: Int = 3
    ): Result<T> {
        var currentRetry = 0
        while (currentRetry < retryCount) {
            try {
                val response = clone().execute()
                return if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    try {
                        val jObjError = JSONObject(
                            response.errorBody()!!.string()
                        )
                        Result.failure(Exception(jObjError.getJSONObject("error").getString("message")))
                    } catch (e: Exception) {
                        Result.failure(Exception("未知错误，请检查域名和密钥是否正确"))
                    }
                }
            } catch (e: IOException) {
                if (currentRetry == retryCount - 1) {
                    return Result.failure(e)
                }
            }
            currentRetry++
            delay(1000)
        }
        return Result.failure(Exception("连接服务器失败，请检查域名是否正确"))
    }

    fun buildRequestBody(map: MutableMap<String, Any?>): RequestBody {
        val m = map.toMap()
        val js = JSONObject(m)
        return js.toString().toRequestBody("application/json".toMediaType())
    }
}
