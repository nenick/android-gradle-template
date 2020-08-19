package de.nenick.wiremock

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import de.nenick.wiremock.internal.WireMockServerController
import de.nenick.wiremock.internal.load
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Provides a mock server with configurable behavior.
 *
 * You can provide initial stubbing definitions which will immediately loaded
 * when the server is up. For that you have to provide a List<MappingBuilder>
 * as koin dependency.
 *
 * See WireMock stubFor as an entry point how to configure the behaviour.
 * - http://wiremock.org/docs/stubbing/
 * - and tools:wiremock-kotlindsl module
 */
class WireMockService : Service(), KoinComponent {

    companion object {
        private const val EXTRA_SSL_PORT = "EXTRA_SSL_PORT"

        /** Convenience method to start this service. */
        fun start(appContext: Context, sslPort: Int) {
            ContextCompat.startForegroundService(appContext, intent(appContext, sslPort))
        }

        fun intent(appContext: Context, sslPort: Int) = Intent(appContext, WireMockService::class.java).apply {
            putExtra(EXTRA_SSL_PORT, sslPort)
        }
    }

    private val initialStubbing by inject<WireMockInitialStubbing>()
    private val mockServer = WireMockServerController()
    private val channelId = javaClass.simpleName
    private val notificationId = channelId.hashCode()

    override fun onCreate() {}

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int) = super.onStartCommand(intent, flags, startId).also {
        startForeground(notificationId, buildNotification())
        mockServer.start(applicationContext, intent.getIntExtra(EXTRA_SSL_PORT, -1))
        initialStubbing.load()
    }

    override fun onTaskRemoved(rootIntent: Intent) = super.onTaskRemoved(rootIntent).also {
        mockServer.stop()
    }

    override fun onBind(intent: Intent): IBinder? {
        return Binder() // No binding features necessary yet.
    }

    private fun buildNotification() = NotificationCompat.Builder(applicationContext, createNotificationChannel())
        .setContentTitle("WireMock is running")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .build()

    private fun createNotificationChannel(): String {
        // Notification channel creation has still to be guarded manually because this is not downward compatible.
        // https://developer.android.com/training/notify-user/channels#CreateChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // With IMPORTANCE_NONE the notification is not shown at all. No need to show it yet.
            val channel = NotificationChannel(channelId, "WireMock Server", NotificationManager.IMPORTANCE_NONE)
            val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
        }
        return channelId // Channel id will not be used further when below android 26 Oreo.
    }
}