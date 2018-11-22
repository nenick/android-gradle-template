package com.template.project.data.local.tools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
abstract class DaoTest : KoinComponent {

    @Before
    fun setupBase() {
        StandAloneContext.loadKoinModules(dataLocalModule, module { single { RuntimeEnvironment.systemContext } })
        ignoreMainThread { database.clearAllTables() }
    }

    val database: ProjectDatabase by inject()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val asBackgroundTask = Dispatchers.IO

    /**
     * Helper to avoid main thread issues.
     *
     * <pre>
     * @Test fun myTest() = ignoreMainThread { /* write test code here */ }
     * </pre>
     */
    fun ignoreMainThread(block: () -> Unit) {
        // runBlocking - waits automatically until async tasks are finished
        return runBlocking(asBackgroundTask) {
            // launch - runs the block async in a non main thread scope
            launch(asBackgroundTask) { block() }.join()
        }
    }

    @After
    fun cleanUpBase() {
        asBackgroundTask.cancel()
        StandAloneContext.stopKoin()
    }
}
