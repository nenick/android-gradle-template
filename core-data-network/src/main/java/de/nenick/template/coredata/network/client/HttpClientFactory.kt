package de.nenick.template.coredata.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import org.koin.core.KoinComponent
import org.koin.core.inject

class HttpClientFactory : KoinComponent {

    private val androidEngineConfig by inject<AndroidEngineConfigDelegate>()

    fun createClient() = HttpClient(Android) {

        // When expectSuccess=true it would turn into exception controlled flow when result code is >= 400.
        expectSuccess = false

        // Keep engine customizable for testing.
        engine(androidEngineConfig.configure())

        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
}