package com.template.project.data.network.tools

import org.koin.dsl.module.module

/**
 * Module dependencies configuration.
 */
val dataNetworkModule = module {
    single { ApiBuilder().todoApi() }
}