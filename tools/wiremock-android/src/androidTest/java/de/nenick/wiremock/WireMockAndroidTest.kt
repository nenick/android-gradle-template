package de.nenick.wiremock

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.BufferedReader
import java.io.File
import java.security.KeyStore
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

@RunWith(AndroidJUnit4::class)
class WireMockAndroidTest {

    @Test
    fun useWireMockForAutomatedDeviceTests() = runBlocking {
        // Read response content from a json file
        val jsonBody = testContext.assets.open("test-response.json").bufferedReader().use(BufferedReader::readText)

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

    private val testContext = InstrumentationRegistry.getInstrumentation().context
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val keyStoreFileName = "wiremock.keystore"
    private val keyStoreType = "BKS"
    private val keyStorePassword = "password"
    private val serverHostname = "127.0.0.1"
    private val serverSslPort = 9999
    private val testEndpoint = "test-api"

    private val client = HttpClient(Android) {
        engine {
            sslManager = { connection ->
                connection.sslSocketFactory = trustOurGeneratedCertificate()
                connection.hostnameVerifier = trustAllHostname()
            }
        }
    }

    @get:Rule
    var wireMockRule = WireMockRule(
        WireMockConfiguration()
            .httpsPort(serverSslPort)
            .keystoreType(keyStoreType)
            .keystorePath(giveKeyStoreFileAnAbsolutePath())
    )

    private fun trustAllHostname() = HostnameVerifier { _, _ -> true }

    private fun giveKeyStoreFileAnAbsolutePath(): String {
        val cacheTarget = File(appContext.cacheDir, keyStoreFileName)
        val keyStoreFileInputStream = appContext.assets.open(keyStoreFileName)
        keyStoreFileInputStream.copyTo(cacheTarget.outputStream())
        return cacheTarget.absolutePath
    }

    private fun trustOurGeneratedCertificate(): SSLSocketFactory {
        // how to use custom certificates https://developer.android.com/training/articles/security-ssl#UnknownCa

        // Create a KeyStore containing our trusted CAs
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(appContext.assets.open(keyStoreFileName), keyStorePassword.toCharArray())

        // Create a TrustManager that trusts the CAs in our KeyStore
        val tmAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmFactory = TrustManagerFactory.getInstance(tmAlgorithm).apply {
            init(keyStore)
        }

        // Create an SSLContext that uses our TrustManager
        val context: SSLContext = SSLContext.getInstance("TLSv1").apply {
            init(null, tmFactory.trustManagers, null)
        }
        return context.socketFactory
    }
}