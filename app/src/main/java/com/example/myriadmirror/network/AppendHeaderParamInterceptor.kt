package com.example.myriadmirror.network

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class AppendHeaderParamInterceptor :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.headers.newBuilder()

        val apiKey = NetConfig.apiKey
        val baseUrl = NetConfig.baseUrl.toHttpUrlOrNull()

        if (!apiKey.isAuthorizationHeaderValid() || apiKey.isEmpty() || baseUrl == null) {
            return chain.proceed(request)
        }

        val newHeader = builder
            .add("Authorization", "Bearer $apiKey")
            .add("Content-Type", CONTENT_TYPE)
            .build()

        val newUrl = request.url.newBuilder()
            .scheme(baseUrl.scheme)
            .host(baseUrl.host)
            .build()

        val newRequest = request.newBuilder()
            .headers(newHeader)
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val CONTENT_TYPE = "application/json"

        fun String.isAuthorizationHeaderValid(): Boolean {
            // 检查是否包含非ASCII字符
            val containsNonAscii = this.any { it.code > 127 }

            // 检查是否包含不可见字符
            val containsControlCharacters = this.any { it.isISOControl() }

            return !containsNonAscii && !containsControlCharacters
        }
    }
}
