package de.nenick.wiremock.testextensions

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

interface WithAndroidContext {
    val testContext: Context
    val appContext: Context
}

object WithAndroidContextExt : WithAndroidContext {
    override val testContext = InstrumentationRegistry.getInstrumentation().context!!
    override val appContext = InstrumentationRegistry.getInstrumentation().targetContext!!
}