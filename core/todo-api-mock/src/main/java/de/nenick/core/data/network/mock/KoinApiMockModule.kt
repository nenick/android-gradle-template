package de.nenick.core.data.network.mock

import de.nenick.core.data.network.mock.internal.AndroidEspresso
import de.nenick.core.data.network.mock.internal.TrustMockServerClientConfig
import de.nenick.core.data.network.mock.stubdata.TodoApiStubs
import de.nenick.template.coredata.network.client.DefaultAndroidClientEngineConfig
import de.nenick.wiremock.WireMockInitialStubbing
import io.ktor.client.*
import io.ktor.client.engine.android.*
import org.koin.dsl.module

object KoinApiMockModule {

    /** Enables to communicate with WireMock instance. */
    val trustWireMock = module {
        factory(override = true) { HttpClient(Android, trustWireMockEngineConfig) }
    }

    /** Lets Espresso automatically wait for ongoing requests. */
    val espressoSupport = module {
        factory(override = true) { HttpClient(AndroidEspresso, trustWireMockEngineConfig) }
    }

    /** Configure default backend behaviour for demo/testing purposes. */
    val defaultStubbing = module {
        factory {
            WireMockInitialStubbing(
                TodoApiStubs.list,
                TodoApiStubs.byId,
                TodoApiStubs.byIdNotFound
            )
        }
    }

    private val trustWireMockEngineConfig: HttpClientConfig<AndroidEngineConfig>.() -> Unit = {
        DefaultAndroidClientEngineConfig.apply(this)
        TrustMockServerClientConfig.apply(this)
    }
}