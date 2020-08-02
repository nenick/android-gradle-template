package de.nenick.template

import de.nenick.core.data.network.mock.ApiMockServer

class OnDeviceServerApplication : TemplateApplication() {
    override fun onCreate() {
        super.onCreate()
        ApiMockServer.start(this)
    }
}