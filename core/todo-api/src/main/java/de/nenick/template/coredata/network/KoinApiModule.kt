package de.nenick.template.coredata.network

import de.nenick.template.coredata.network.client.DefaultAndroidClientEngineConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

object KoinApiModule {
    val default = module {
        factory { HttpClient(Android, DefaultAndroidClientEngineConfig::apply) }
        factory { TodoApi("https://localhost:9999") }
    }
}