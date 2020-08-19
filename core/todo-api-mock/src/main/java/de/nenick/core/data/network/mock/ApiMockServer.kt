package de.nenick.core.data.network.mock

import android.content.Context
import de.nenick.wiremock.WireMockService
import org.koin.core.context.loadKoinModules

object ApiMockServer {
    fun start(appContext: Context) {
        loadKoinModules(listOf(KoinApiMockModule.trustWireMock, KoinApiMockModule.defaultStubbing))
        WireMockService.start(appContext, 9999)
    }
}