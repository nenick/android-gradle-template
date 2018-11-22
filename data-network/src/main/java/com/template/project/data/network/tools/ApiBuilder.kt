package com.template.project.data.network.tools

import com.template.project.data.network.TodoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.standalone.KoinComponent
import org.koin.standalone.getProperty
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder : KoinComponent {

    private val apiUrl = getProperty<String>("api_url")
    private val apiKey = getProperty<String>("api_key")
    private val logLevel = HttpLoggingInterceptor.Level.valueOf(getProperty("api_log_level"))

    fun todoApi(): TodoApi {

        val client = OkHttpClient().newBuilder()
            .addInterceptor(ApiKeyRequestInterceptor(apiKey))
            .addInterceptor(HttpLoggingInterceptor().apply { level = logLevel })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TodoApi::class.java)
    }
}