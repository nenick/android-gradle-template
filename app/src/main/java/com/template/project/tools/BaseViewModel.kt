package com.template.project.tools

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.androidannotations.api.KotlinOpen
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * The ViewModel encapsulates presentation logic and state.
 *
 * Presentation logic is defined here as the application logic that is concerned with the application’s
 * use-cases (or user stories, user tasks, workflow, etc) and defines the logical behavior and structure
 * of the application. The ViewModel is NOT a control’s code behind. Presentation logic should have no idea
 * what its visual representation will be like and should be independent of any specific user interface
 * implementation. To maximize re-use opportunities, the ViewModel should have no reference to any specific
 * UI classes, elements, controls or behavior (like animations). It should not derive from any UI base class
 * (ideally it should be a POCO). It should (of course!) be unit testable and be able to function without any UI at all.
 *
 * @see: https://blogs.msdn.microsoft.com/dphill/2009/01/31/the-viewmodel-pattern/
 */
abstract class BaseViewModel : ViewModel(), KoinComponent {

    private val background by inject<CoroutineDispatcher>()

    fun async(block: suspend () -> Unit) {
        CoroutineScope(background).async { block() }
    }
}