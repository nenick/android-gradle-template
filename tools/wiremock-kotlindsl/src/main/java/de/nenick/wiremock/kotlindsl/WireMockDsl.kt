package de.nenick.wiremock.kotlindsl

import com.github.tomakehurst.wiremock.client.WireMock
import de.nenick.wiremock.kotlindsl.scopes.StubScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Use this to start with stubbing.
 */
object WireMockDsl {
    fun stubFor(init: StubScope.() -> Unit) {
        val stubScope = StubScope().apply(init)
        runBlocking(Dispatchers.IO) { stubScope.builders.forEach { WireMock.stubFor(it) } }
    }
}