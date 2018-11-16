package com.template.project.data.network.tools

import org.koin.dsl.module.module

val dataNetworkModule = module {
    single { ApiBuilder().todoApi() }
}