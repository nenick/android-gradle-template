package de.nenick.template.coredata.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature

object DefaultAndroidClientEngineConfig {
    fun apply(target: HttpClientConfig<AndroidEngineConfig>) {
        // When expectSuccess=true it would turn into exception controlled flow when result code is >= 400.
        target.expectSuccess = false

        target.install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
}