package com.template.project.tests

import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.google.common.truth.Truth.assertThat
import com.template.project.R
import com.template.project.tests.tools.AppTest
import org.junit.Test

class ContentSampleTest : AppTest() {

    private val todoItemFromServer = "delectus aut autem"

    @Test
    fun showsTodosFromServer() {
        onView(ViewMatchers.withId(R.id.btn_next)).perform(ViewActions.click())
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
}
