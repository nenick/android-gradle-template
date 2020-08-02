package de.nenick.wiremock.kotlindsl

import de.nenick.wiremock.kotlindsl.scopes.MappingScope
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class WireMockStubTest {

    @Test
    fun `stubbing have by default a low priority`() {
        expectThat(MappingScope().priority).isEqualTo(100)
    }
}