package de.nenick.core.data.network.mock.internal

import io.ktor.client.engine.HttpClientEngineBase
import io.ktor.client.engine.android.AndroidClientEngine
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Mimics the [AndroidClientEngine] to enable the usage of an [EspressoTrackedDispatcher] for api calls.
 */
class AndroidTestEngineConfig(override val config: AndroidEngineConfig) : HttpClientEngineBase("ktor-android") {

    private val origin = AndroidClientEngine(config)

    override val dispatcher: CoroutineDispatcher
        // We want to use TestCoroutineDispatcher instead of IO for more control but current this one is by default in main thread.
        get() = EspressoTrackedDispatcher("api-calls", Dispatchers.IO)

    @InternalAPI
    override suspend fun execute(data: HttpRequestData): HttpResponseData {
        // Origin engine will use our overridden coroutine dispatcher.
        return origin.execute(data)
    }
}

