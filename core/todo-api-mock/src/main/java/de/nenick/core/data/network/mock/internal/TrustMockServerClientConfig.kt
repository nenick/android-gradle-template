package de.nenick.core.data.network.mock.internal

import android.content.Context
import de.nenick.wiremock.HttpClientTrustUtils
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Allows the Android HTTP engine to communicate with our provided WireMock instance. Otherwise it would complain
 * about untrusted certificates and host names.
 */
object TrustMockServerClientConfig : KoinComponent {

    private val appContext by inject<Context>()

    fun apply(target: HttpClientConfig<AndroidEngineConfig>) {
        target.engine {
            sslManager = { connection ->
                connection.sslSocketFactory = HttpClientTrustUtils.trustOurGeneratedCertificate(appContext)
                connection.hostnameVerifier = HttpClientTrustUtils.trustAllHostname()
            }
        }
    }
}