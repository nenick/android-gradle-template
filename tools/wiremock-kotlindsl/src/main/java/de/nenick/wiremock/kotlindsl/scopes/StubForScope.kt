package de.nenick.wiremock.kotlindsl.scopes

import com.github.tomakehurst.wiremock.client.MappingBuilder
import de.nenick.wiremock.kotlindsl.WireMockStub

class StubForScope {

    fun get(init: MappingScope.() -> Unit) = add(WireMockStub.get(init))
    fun post(init: MappingScope.() -> Unit) = add(WireMockStub.post(init))
    fun put(init: MappingScope.() -> Unit) = add(WireMockStub.put(init))
    fun delete(init: MappingScope.() -> Unit) = add(WireMockStub.delete(init))
    fun patch(init: MappingScope.() -> Unit) = add(WireMockStub.patch(init))
    fun options(init: MappingScope.() -> Unit) = add(WireMockStub.options(init))
    fun head(init: MappingScope.() -> Unit) = add(WireMockStub.head(init))
    fun trace(init: MappingScope.() -> Unit) = add(WireMockStub.trace(init))
    fun any(init: MappingScope.() -> Unit) = add(WireMockStub.any(init))

    internal val builders = mutableListOf<MappingBuilder>()

    private fun add(mapping: MappingBuilder) {
        builders.add(mapping)
    }
}