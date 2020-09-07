package de.nenick.wiremock.kotlindsl

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import de.nenick.wiremock.kotlindsl.scopes.StubForScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Use this to start with stubbing.
 */
object WireMockDsl {
    fun stubFor(init: StubForScope.() -> Unit) {
        val stubScope = StubForScope().apply(init)
        runBlocking(Dispatchers.IO) { stubScope.builders.forEach { WireMock.stubFor(it) } }
    }

    fun stubFor(stub: MappingBuilder) {
        WireMock.stubFor(stub)
    }
}