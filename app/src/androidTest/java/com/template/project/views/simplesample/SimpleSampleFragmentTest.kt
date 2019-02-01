package com.template.project.views.simplesample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.*
import com.template.project.R
import com.template.project.tools.FragmentTest
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class SimpleSampleFragmentTest : FragmentTest() {

    private val textInputResult = MutableLiveData<String>()
    private val viewModelMock = mock<SimpleSampleViewModel>().apply {
        whenever(textInput).thenReturn(textInputResult)
    }

    override fun mocks() = module {
        viewModel { viewModelMock }
    }

    @Before
    fun setUp() {
        start(SimpleSampleFragment_.builder().build())
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
        Espresso.closeSoftKeyboard()
        whenClickNavigateToNext()
        verify(navigationMock).toContentSample(any())
    }

    private fun whenClickNavigateToNext() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_next))
            .perform(ViewActions.click())
    }

    private fun assertViewModelGotNewInput(text: String) {
        Truth.assertThat(textInputResult.value).isEqualTo(text)
    }

    private fun whenChangeTextInput(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.textInput))
            .perform(ViewActions.replaceText(text))
    }

    private fun whenModelChangeText(text: String) {
            textInputResult.postValue(text)
    }

    private fun assertCurrentMessage(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.textView))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }
}