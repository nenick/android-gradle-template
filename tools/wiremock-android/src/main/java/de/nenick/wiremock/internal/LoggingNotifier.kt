package de.nenick.wiremock.internal

import android.util.Log
import com.github.tomakehurst.wiremock.common.Notifier

/**
 * Logs WireMock reports which is helpful for error investigations.
 */
internal object LoggingNotifier : Notifier {

    private const val tag = "WireMock"

    override fun info(message: String) {
        Log.i(tag, message)
    }

    override fun error(message: String) {
        Log.e(tag, message)
    }

    override fun error(message: String, t: Throwable) {
        Log.e(tag, message, t)
    }
}