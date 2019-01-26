package com.template.project.data.network.tools

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.SuspendCallAdapter
import java.lang.reflect.Type
import kotlin.coroutines.resume

/**
 * Modifies the response behavior of Retrofit when generating the api response wrapper.
 *
 * Mainly makes the dependent modules independent of the used framework for network calls.
 * Also avoid exception controlled flows.
 */
class IndependentCallAdapter<R>(private val responseType: Type) : SuspendCallAdapter<R, ApiResponse<R>> {

    override fun responseType(): Type {
        return responseType
    }

    override suspend fun adapt(call: Call<R>): ApiResponse<R> {
        return call.await()
    }
}

suspend fun <T> Call<T>.await(): ApiResponse<T> {
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                @Suppress("UNCHECKED_CAST")
                continuation.resume(ApiResponse.create(response))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resume(ApiResponse.create(t))
            }
        })
    }
}