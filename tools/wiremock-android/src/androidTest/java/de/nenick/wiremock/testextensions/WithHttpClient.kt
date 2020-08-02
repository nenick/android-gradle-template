package de.nenick.wiremock.testextensions

import de.nenick.wiremock.HttpClientTrustUtils
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

interface WithHttpClient {
    val serverHostname: String
    val serverSslPort: Int
    val testEndpoint: String
    val client: HttpClient
}

object WithHttpClientExt : WithHttpClient, WithAndroidContext by WithAndroidContextExt {

    override val serverHostname = "127.0.0.1"
    override val serverSslPort = 9999
    override val testEndpoint = "test-api"

    override val client = HttpClient(Android) {
        engine {
            sslManager = { connection ->
                connection.sslSocketFactory =
                    HttpClientTrustUtils.trustOurGeneratedCertificate(appContext)
                connection.hostnameVerifier = HttpClientTrustUtils.trustAllHostname()
            }
        }
    }
}