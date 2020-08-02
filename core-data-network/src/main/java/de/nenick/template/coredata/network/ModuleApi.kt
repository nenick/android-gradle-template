package de.nenick.template.coredata.network

import de.nenick.template.coredata.network.client.AndroidEngineConfigDelegate
import de.nenick.template.coredata.network.client.HttpClientFactory
import org.koin.dsl.module

val CoreDateNetworkModule = module {
    single { HttpClientFactory().createClient() }
    factory { AndroidEngineConfigDelegate() }
    factory { TodoApi("https://localhost:9999") }
}