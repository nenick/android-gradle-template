package de.nenick.wiremock.internal

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

internal fun List<MappingBuilder>.load() {
    runBlocking(Dispatchers.IO) { forEach { WireMock.stubFor(it) } }
}
