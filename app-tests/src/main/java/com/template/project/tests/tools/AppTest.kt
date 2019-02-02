package com.template.project.tests.tools

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.template.project.MainActivity_
import com.template.project.R
import org.junit.Rule
import org.junit.runner.RunWith

/** Base for running instrumentation with ready application. */
@RunWith(AndroidJUnit4::class)
abstract class AppTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity_::class.java)

    fun clickUpButton() {
        Espresso.onView(ViewMatchers.withContentDescription(R.string.abc_action_bar_up_description))
            .perform(ViewActions.click())
    }
}
