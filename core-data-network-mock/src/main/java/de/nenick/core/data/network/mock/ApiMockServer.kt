package de.nenick.core.data.network.mock

import android.content.Context
import de.nenick.core.data.network.mock.internal.TrustMockServerConfig
import de.nenick.template.coredata.network.client.AndroidEngineConfigDelegate
import de.nenick.wiremock.WireMockInitialStubbing
import de.nenick.wiremock.WireMockService
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object ApiMockServer {
    fun start(appContext: Context) {
        loadKoinModules(
            module {
                factory<AndroidEngineConfigDelegate>(override = true) { TrustMockServerConfig() }
                factory {
                    WireMockInitialStubbing(
                        TodoApiStubs.list,
                        TodoApiStubs.byId,
                        TodoApiStubs.byIdNotFound
                    )
                }
            }
        )
        WireMockService.start(appContext, 9999)
    }
}