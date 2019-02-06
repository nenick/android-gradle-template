package com.template.project.data.local.tools

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
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

@RunWith(RobolectricTestRunner::class)
abstract class DaoTestDefaults : KoinComponent {

    /**
     * Avoid the RuntimeException: Method getMainLooper in android.os.Looper not mocked.
     */
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    init {
        StandAloneContext.startKoin(listOf(

            // Use the real Room database dependencies/configuration with use of robolectric support.
            dataLocalModule,

            module {

                // Since androidx supports robolectric we can used it for accessing a "real" application context.
                single { ApplicationProvider.getApplicationContext() as Context}
            }
        ))
    }

    private val database: ProjectDatabase by inject()

    @Before
    fun setupBase() {

        // Ensure a clean testing base by clearing all table contents.
        ignoreMainThread { database.clearAllTables() }
    }

    /**
     * Run test code in non main thread and wait for execution result.
     *
     * <pre>
     * @Test fun myTest() = ignoreMainThread { /* write test code here */ }
     * </pre>
     */
    fun ignoreMainThread(block: () -> Unit) {
        return runBlocking(Dispatchers.IO) { block() }
    }

    @After
    fun cleanUpBase() {

        // Stop dependency injection framework to clean up stuff like singletons.
        StandAloneContext.stopKoin()
    }
}
