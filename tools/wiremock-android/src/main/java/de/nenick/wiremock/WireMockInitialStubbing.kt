package de.nenick.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder

class WireMockInitialStubbing(vararg stubbing: MappingBuilder) : ArrayList<MappingBuilder>(stubbing.asList())