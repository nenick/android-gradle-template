package de.nenick.template

import android.app.Application
import de.nenick.template.coredata.network.CoreDateNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class TemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TemplateApplication)
            modules(CoreDateNetworkModule)
        }
    }
}