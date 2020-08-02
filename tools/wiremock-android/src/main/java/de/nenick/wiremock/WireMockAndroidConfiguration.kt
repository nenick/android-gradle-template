package de.nenick.wiremock

import android.content.Context
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import de.nenick.wiremock.internal.LoggingNotifier
import java.io.File

class WireMockAndroidConfiguration(appContext: Context) : WireMockConfiguration() {

    init {
        notifier(LoggingNotifier)
        keystoreType(HttpClientTrustUtils.keyStoreType)
        keystorePath(cacheKeyStoreToProvideAbsolutePath(appContext))
    }

    private fun cacheKeyStoreToProvideAbsolutePath(appContext: Context): String {
        val cacheTarget = File(
            appContext.cacheDir,
            HttpClientTrustUtils.keyStoreFileName
        )
        val keyStoreFileInputStream = appContext.assets.open(HttpClientTrustUtils.keyStoreFileName)
        keyStoreFileInputStream.copyTo(cacheTarget.outputStream())
        return cacheTarget.absolutePath
    }
}