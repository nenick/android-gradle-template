package com.template.project.specs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.template.project.MainActivity_
import com.template.project.R
import com.template.project.specs.tools.AppSpecDefaults
import org.junit.Test

class SimpleSampleTest : AppSpecDefaults() {

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
