package com.template.project.it

import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth.assertThat
import com.template.project.MainActivity_
import com.template.project.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentSampleTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity_::class.java)

    private val todoItemFromServer = "delectus aut autem"

    @Test
    fun showsTodosFromServer() {
        onView(ViewMatchers.withId(R.id.btn_next)).perform(ViewActions.click())

        // TODO stabilize through idling resources concept
        Thread.sleep(2000)

        onView(ViewMatchers.withText(todoItemFromServer)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigation() {
        val navController = Navigation.findNavController(activityRule.activity, R.id.nav_host_fragment)

        onView(ViewMatchers.withId(R.id.btn_next)).perform(ViewActions.click())
        assertThat(navController.currentDestination!!.id).isEqualTo(R.id.contentSampleFragment)

        clickUpButton()
        assertThat(navController.currentDestination!!.id).isNotEqualTo(R.id.contentSampleFragment)
    }

    private fun clickUpButton() {
        onView(ViewMatchers.withContentDescription(R.string.abc_action_bar_up_description)).perform(ViewActions.click())
    }
}
