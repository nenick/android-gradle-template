package de.nenick.template.coredata.network.client

import io.ktor.client.engine.android.AndroidEngineConfig

/**
 * Allows to adjust the Android HTTP engine configuration when mocking or testing with self provided servers.
 *
 * Inherit this class and provide it as an overridden koin dependency.
 */
open class AndroidEngineConfigDelegate {
    open fun configure(): AndroidEngineConfig.() -> Unit = {}
}