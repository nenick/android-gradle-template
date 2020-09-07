package de.nenick.template

import androidx.activity.viewModels
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import de.nenick.core.data.network.mock.stubdata.TodoTestData
import de.nenick.coroutines.test.MainCoroutinesDispatcherRule
import de.nenick.coroutines.test.MainCoroutinesDispatcherRule.Companion.coAnswersDelayed
import de.nenick.template.coredata.network.TodoApi
import de.nenick.template.coredata.network.models.ApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTestRule

/**
 * Instrumented test, which will execute on an Android device with mocked classes.
 */
class ExampleAppInstrumentedTest {

    @get:Rule
    val koinRule = KoinTestRule.create { modules(module { single { todoApi } }) }

    @get:Rule
    var mainDispatcherRule = MainCoroutinesDispatcherRule()

    @get:Rule
    var activityRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    private val todoApi = mockk<TodoApi>()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("de.nenick.template", appContext.packageName)
    }

    @Test
    fun sampleClickFlow() {
        coEvery { todoApi.todoById(any()) } coAnswersDelayed { ApiResponse.Success(TodoTestData.task4Completed) }

        onView(withId(R.id.textView)).check(matches(withText("Hello World!")))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText("doing request")))

        mainDispatcherRule.deliverNextDelayedAnswer()

        onView(withId(R.id.textView)).check(matches(withText("My Task 4")))
    }

    @Test
    fun sampleDirectStateChange() {
        onView(withId(R.id.textView)).check(matches(withText("Hello World!")))
        activityRule.scenario.onActivity {
            it.viewModels<MainViewModel>().value.result.value = "direct changed"
        }
        onView(withId(R.id.textView)).check(matches(withText("direct changed")))
    }
}