package com.template.project.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.template.project.MainActivity_
import com.template.project.R
import com.template.project.tests.tools.AppTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class SimpleSampleTest : AppTest() {

    @Test
    fun showTextInput() {
        onView(ViewMatchers.withId(R.id.textView))
            .check(ViewAssertions.matches(ViewMatchers.withText("Hello World!")))

        onView(ViewMatchers.withId(R.id.textInput))
            .perform(ViewActions.replaceText("Changed"))

        onView(ViewMatchers.withId(R.id.textView))
            .check(ViewAssertions.matches(ViewMatchers.withText("Changed")))
    }
}
