package com.template.project.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.template.project.MainActivity_
import com.template.project.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleSampleTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity_::class.java)

    @Test
    fun showHelloWorld() {
        onView(ViewMatchers.withId(R.id.textView))
            .check(ViewAssertions.matches(ViewMatchers.withText("Hello World!")))
    }
}
