package com.template.project.data.network.tools

import com.template.project.data.network.TodoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module dependencies configuration.
 */
val dataNetworkModule = module {

    single {
        OkHttpClient().newBuilder()
            .addInterceptor(ApiKeyRequestInterceptor(getProperty("api_key")))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.valueOf(getProperty("api_log_level"))
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(getProperty<String>("api_url"))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addSuspendCallAdapterFactory(IndependentCallAdapterFactory())
            .build()
    }

    single { get<Retrofit>().create(TodoApi::class.java) }
}
