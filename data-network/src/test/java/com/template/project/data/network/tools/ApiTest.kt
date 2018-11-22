package com.template.project.data.network.tools

import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

abstract class ApiTest {

    init {
        StandAloneContext.loadKoinModules(module {})

        StandAloneContext.getKoin().propertyResolver.add("api_url", "https://jsonplaceholder.typicode.com")
        StandAloneContext.getKoin().propertyResolver.add("api_key", "dummy_key")
        StandAloneContext.getKoin().propertyResolver.add("api_log_level", HttpLoggingInterceptor.Level.NONE.name)
    }

    @After
    fun cleanUpBase() {
        StandAloneContext.stopKoin()
    }
}