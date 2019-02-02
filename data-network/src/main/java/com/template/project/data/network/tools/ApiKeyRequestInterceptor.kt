package com.template.project.data.network.tools

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Use an api key for each request.
 */
class ApiKeyRequestInterceptor(

    private val apiKey: String

) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}
