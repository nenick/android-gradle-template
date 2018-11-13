package com.template.project.data.local.tools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.template.project.data.local.ProjectDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.reflect.Field

@RunWith(RobolectricTestRunner::class)
abstract class DaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val asBackgroundTask = Dispatchers.IO

    val database = ProjectDatabase.getDatabase(ApplicationProvider.getApplicationContext())

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
    fun releaseSingletons() {
        // this step reset the changes you have done to singleton instances
        // otherwise this changes could effect your next test method execution
        resetSingleton(ProjectDatabase::class.java, "INSTANCE")
    }

    /** Nulls the static field on given class. */
    private fun resetSingleton(clazz: Class<*>, fieldName: String) {
        val instance: Field
        try {
            instance = clazz.getDeclaredField(fieldName)
            instance.isAccessible = true
            instance.set(null, null)
        } catch (e: Exception) {
            throw RuntimeException()
        }

    }
}
