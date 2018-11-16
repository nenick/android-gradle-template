package com.template.project.data.network.tools

import com.template.project.data.network.TodoApi
import com.template.project.datanetwork.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder {

    fun todoApi(): TodoApi {

        val client = OkHttpClient().newBuilder()
            .addInterceptor(ApiKeyRequestInterceptor("dummy api key"))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TodoApi::class.java)
    }
}