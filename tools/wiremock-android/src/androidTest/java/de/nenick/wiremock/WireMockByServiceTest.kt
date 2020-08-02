package de.nenick.wiremock

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ServiceTestRule
import com.github.tomakehurst.wiremock.client.WireMock.*
import de.nenick.wiremock.testextensions.WithAndroidContext
import de.nenick.wiremock.testextensions.WithAndroidContextExt
import de.nenick.wiremock.testextensions.WithHttpClient
import de.nenick.wiremock.testextensions.WithHttpClientExt
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.dsl.module
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.BufferedReader

@RunWith(AndroidJUnit4::class)
class WireMockByServiceTest :
    WithHttpClient by WithHttpClientExt,
    WithAndroidContext by WithAndroidContextExt {

    @get:Rule
    val serviceRule = ServiceTestRule()

    @Test
    fun startAndUseServer() = runBlocking {
        // Prepare mock response
        val jsonBody = testContext.assets.open("test-response.json").bufferedReader().use(BufferedReader::readText)
        val mapping = get(urlMatching("/$testEndpoint")).willReturn(
            aResponse()
                .withStatus(200)
                .withBody(jsonBody)
        )
        startKoin { modules(module { factory { WireMockInitialStubbing(mapping) } }) }

        // Start mock server.
        serviceRule.startService(WireMockService.intent(appContext, serverSslPort))

        // Call api for mocked response.
        val response = client.get<HttpResponse>("https://$serverHostname:$serverSslPort/$testEndpoint")

        // Expect call was successful mocked.
        expectThat(response) {
            get { status }.isEqualTo(HttpStatusCode.OK)
            getBlocking { receive<String>() }.isEqualTo(
                """{
                |  "test": "this test works"
                |}""".trimMargin()
            )
        }
    }
}