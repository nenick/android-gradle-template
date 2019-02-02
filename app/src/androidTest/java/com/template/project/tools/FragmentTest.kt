package com.template.project.tools

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.mock
import com.template.project.ProjectNavigation
import com.template.project.views.simplesample.SimpleSampleFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

/** Provides basic setup for testing Fragments isolated */
@RunWith(AndroidJUnit4::class)
abstract class FragmentTest {

    @get:Rule
    val testActivityRule = ActivityTestRule(SingleFragmentActivity::class.java)

    abstract fun mocks(): Module

    val navigationMock = mock<ProjectNavigation>()

    private val commonMocks = module {
        single { navigationMock }
    }

    @Before
    fun setUpBase() {
        StandAloneContext.loadKoinModules(mocks(), commonMocks)
    }

    fun start(fragment: SimpleSampleFragment) {
        testActivityRule.activity.setFragment(fragment)
    }

    @After
    fun cleanUpBase() {
        StandAloneContext.stopKoin()
    }
}
