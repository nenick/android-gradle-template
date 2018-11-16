package com.template.project.views.simplesample

import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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

class SimpleSampleFragmentTest : FragmentTest() {

    private val viewModelMock = mock<SimpleSampleViewModel>()

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
        testActivityRule.activity.runOnUiThread {
            captor.firstValue.onChanged(text)
        }
    }

    private fun assertCurrentMessage(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.textView))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }
}