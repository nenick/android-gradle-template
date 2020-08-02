package de.nenick.wiremock.internal

import android.content.Context
import com.github.tomakehurst.wiremock.WireMockServer
import de.nenick.wiremock.WireMockAndroidConfiguration

internal class WireMockServerController {

    private lateinit var wireMock: WireMockServer

    var isRunning = false
        private set

    fun start(appContext: Context, sslPort: Int) {
        wireMock = WireMockServer(WireMockAndroidConfiguration(appContext).httpsPort(sslPort))
        wireMock.start()
        isRunning = true
    }

    fun stop() {
        wireMock.stop()
        isRunning = false
    }
}