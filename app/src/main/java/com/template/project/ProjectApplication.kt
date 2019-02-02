package com.template.project

import android.app.Application
import com.template.project.data.local.tools.dataLocalModule
import com.template.project.data.network.tools.dataNetworkModule
import com.template.project.tools.appModule
import org.koin.android.ext.android.startKoin

open class ProjectApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialise the dependency injection framework
        startKoin(this, modulesDependencies(), loadPropertiesFromFile = true)
    }

    // intended to be overwritten when testing
    open fun modulesDependencies() = listOf(appModule, dataLocalModule, dataNetworkModule)
}
