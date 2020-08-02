package de.nenick.wiremock

import android.content.Context
import java.security.KeyStore
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

object HttpClientTrustUtils {

    internal const val keyStoreType = "BKS"
    internal const val keyStoreFileName = "wiremock.keystore"
    private const val keyStorePassword = "password"
    // See SSLContext.class documentation for all available algorithm and the android version limitations.
    private const val latestSupportedAlgorithm = "TLSv1"

    fun trustAllHostname() = HostnameVerifier { _, _ -> true }

    fun trustOurGeneratedCertificate(appContext: Context): SSLSocketFactory {
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
        val context: SSLContext = SSLContext.getInstance(latestSupportedAlgorithm).apply {
            init(null, tmFactory.trustManagers, null)
        }
        return context.socketFactory
    }
}