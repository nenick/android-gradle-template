package com.template.project.test.tools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.template.project.tools.ProjectDispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.scheduling.ExperimentalCoroutineDispatcher
import org.junit.After
import org.junit.Rule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import kotlin.coroutines.CoroutineContext

@InternalCoroutinesApi
abstract class ViewModelTestDefaults: KoinComponent {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    val coroutineDispatcher = ProjectDispatchers(ExperimentalCoroutineDispatcher(), object:ExperimentalCoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            block.run()
        }
    })

    @After
    fun cleanDefaults() {
        StandAloneContext.stopKoin()
    }
}
