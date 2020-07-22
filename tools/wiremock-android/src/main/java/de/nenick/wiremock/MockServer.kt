package de.nenick.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration

class MockServer {

    private val wireMock = WireMockServer(WireMockConfiguration())

    fun start() {
        wireMock.start()
    }

    fun stop() {
        wireMock.stop()
    }
}