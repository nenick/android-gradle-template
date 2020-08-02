package de.nenick.wiremock.kotlindsl

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.http.RequestMethod
import de.nenick.wiremock.kotlindsl.scopes.MappingScope

/**
 * Use this directly when you want to prepare common stubbing.
 */
object WireMockStub {

    fun get(init: MappingScope.() -> Unit) = createMapping(RequestMethod.GET, init)
    fun post(init: MappingScope.() -> Unit) = createMapping(RequestMethod.POST, init)
    fun put(init: MappingScope.() -> Unit) = createMapping(RequestMethod.PUT, init)
    fun delete(init: MappingScope.() -> Unit) = createMapping(RequestMethod.DELETE, init)
    fun patch(init: MappingScope.() -> Unit) = createMapping(RequestMethod.PATCH, init)
    fun options(init: MappingScope.() -> Unit) = createMapping(RequestMethod.OPTIONS, init)
    fun head(init: MappingScope.() -> Unit) = createMapping(RequestMethod.HEAD, init)
    fun trace(init: MappingScope.() -> Unit) = createMapping(RequestMethod.TRACE, init)
    fun any(init: MappingScope.() -> Unit) = createMapping(RequestMethod.ANY, init)

    private fun createMapping(method: RequestMethod, init: MappingScope.() -> Unit): MappingBuilder {
        val scope = MappingScope().apply(init)
        return WireMock.request(method.name, scope.url.pattern)
            .atPriority(scope.priority)
            .willReturn(scope.willReturn.builder)
    }
}