package de.nenick.wiremock

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import strikt.api.Assertion
import strikt.api.DescribeableBuilder
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class WireMockJvmTest {

    @Test
    fun useWireMockForAutomatedTests() = runBlocking {
        // Read response content from a json file
        val jsonBody = javaClass.getResource("test-response.json")!!.readText()

        // Prepare mock response
        stubFor(
            get(urlMatching("/$testEndpoint")).willReturn(
                aResponse()
                    .withStatus(200)
                    .withBody(jsonBody)
            )
        )

        // Call api for mocked response
        val response = client.get<HttpResponse>("https://$serverHostname:$serverSslPort/$testEndpoint")

        // Expect call was successful mocked
        expectThat(response) {
            get { status }.isEqualTo(HttpStatusCode.OK)
            getBlocking { receive<String>() }.isEqualTo(
                """{
                |  "test": "this test works"
                |}""".trimMargin()
            )
        }
    }

    private val serverHostname = "127.0.0.1"
    private val serverSslPort = 9999
    private val testEndpoint = "test-api"

    private val client = HttpClient()

    @get:Rule
    var wireMockRule = WireMockRule(WireMockConfiguration())
}

fun <R> Assertion.Builder<HttpResponse>.getBlocking(function: suspend HttpResponse.() -> R): DescribeableBuilder<R> {
    return get { runBlocking { function() } }
}