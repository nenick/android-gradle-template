package de.nenick.core.data.network.mock.internal

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.AndroidEngineConfig

/**
 * Mimics [io.ktor.client.engine.android.Android] to make engine more configurable.
 */
object AndroidTestClientEngineFactory : HttpClientEngineFactory<AndroidEngineConfig> {
    override fun create(block: AndroidEngineConfig.() -> Unit): HttpClientEngine =
        AndroidTestEngineConfig(AndroidEngineConfig().apply(block))
}