package com.template.project.test.tools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.scheduling.ExperimentalCoroutineDispatcher
import org.junit.After
import org.junit.Rule
import org.koin.standalone.StandAloneContext

@InternalCoroutinesApi
abstract class RepositoryTestDefaults {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    val coroutineDispatcher = ExperimentalCoroutineDispatcher() as CoroutineDispatcher

    @After
    fun cleanDefaults() {
        StandAloneContext.stopKoin()
    }
}

