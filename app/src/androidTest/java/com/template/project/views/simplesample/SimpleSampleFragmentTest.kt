package com.template.project.views.simplesample

import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.template.project.R
import com.template.project.tools.FragmentTest
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiObject



class SimpleSampleFragmentTest : FragmentTest() {

    private val viewModelMock = mock<SimpleSampleViewModel>()

    override fun mocks() = module {
        viewModel { viewModelMock }
    }

    @Before
    fun setUp() {
        start(SimpleSampleFragment_.builder().build())

        // TODO use library for stabilizing tests
        // sometimes a system process isn't responding on emulator and this must be confirmed
        if (dialogIsShownWith(stringResourceByName("anr_process", ".*").replace("?", "\\?"))) {
            click(stringResourceByName("wait"))
        }
        // sometimes a system process does crash on emulator and this must be confirmed
        if (dialogIsShownWith(stringResourceByName("aerr_application", ".*"))) {
            click(stringResourceByName("ok"))
        }

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val anyDialogButton = device.findObject(UiSelector().clickable(true))
        if(anyDialogButton.exists()) anyDialogButton.click()
    }

    private fun dialogIsShownWith(expectedMessage: String): Boolean {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val dialog = device.findObject(UiSelector().textMatches(expectedMessage))
        return dialog.exists()
    }

    private fun click(target: String) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val button = device.findObject(UiSelector().text(target))

        try {
            button.click()
        } catch (e: UiObjectNotFoundException) {
            throw IllegalStateException(e)
        }

    }

    private fun stringResourceByName(name: String, vararg formatArgs: String): String {
        // for all available strings see Android/sdk/platforms/android-23/data/res/values/strings.xml
        val resId = InstrumentationRegistry.getInstrumentation().context.resources.getIdentifier(name, "string", "android")
        return InstrumentationRegistry.getInstrumentation().context.getString(resId, *formatArgs)
    }

    @Test
    fun changeViewModelData() {
        assertCurrentMessage("Hello World!")
        whenModelChangeText("Some other data")
        assertCurrentMessage("Some other data")
    }

    @Test
    fun changeTextInput() {
        whenChangeTextInput("Text has changed")
        assertViewModelGotNewInput("Text has changed")
    }

    @Test
    fun navigate() {
        whenClickNavigateToNext()
        verify(navigationMock).toContentSample(any())
    }

    private fun whenClickNavigateToNext() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_next))
            .perform(ViewActions.click())
    }

    private fun assertViewModelGotNewInput(text: String) {
        verify(viewModelMock).updateTextInput(text)
    }

    private fun whenChangeTextInput(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.textInput))
            .perform(ViewActions.replaceText(text))
    }

    private fun whenModelChangeText(text: String) {
        val captor = argumentCaptor<Observer<in String>>()
        verify(viewModelMock).observeTextInput(any(), captor.capture())
        testActivityRule.runOnUiThread {
            captor.firstValue.onChanged(text)
        }
    }

    private fun assertCurrentMessage(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.textView))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }
}