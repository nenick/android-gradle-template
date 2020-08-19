package de.nenick.core.data.network.mock

import de.nenick.core.data.network.mock.internal.AndroidTestClientEngineFactory
import de.nenick.core.data.network.mock.internal.TrustMockServerClientConfig
import de.nenick.core.data.network.mock.stubdata.TodoApiStubs
import de.nenick.template.coredata.network.client.DefaultAndroidClientEngineConfig
import de.nenick.wiremock.WireMockInitialStubbing
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

object KoinApiMockModule {

    val trustWireMock = module {
        factory(override = true) {
            HttpClient(Android) {
                DefaultAndroidClientEngineConfig.apply(this)
                TrustMockServerClientConfig.apply(this)
            }
        }
    }

    val espressoSupport = module {
        factory(override = true) {
            HttpClient(AndroidTestClientEngineFactory) {
                DefaultAndroidClientEngineConfig.apply(this)
                TrustMockServerClientConfig.apply(this)
            }
        }
    }

    val defaultStubbing = module {
        factory {
            WireMockInitialStubbing(
                TodoApiStubs.list,
                TodoApiStubs.byId,
                TodoApiStubs.byIdNotFound
            )
        }
    }
}

