package com.template.project.tools

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * The ViewModel encapsulates presentation logic and state.
 *
 * Presentation logic is defined here as the application logic that is concerned with the application’s
 * use-cases (or user stories, user tasks, workflow, etc) and defines the logical behavior and structure
 * of the application.
 *
 * The ViewModel is NOT a control’s code behind. Presentation logic should have no idea
 * what its visual representation will be like and should be independent of any specific user interface
 * implementation. To maximize re-use opportunities, the ViewModel should have no reference to any specific
 * UI classes, elements, controls or behavior (like animations). It should not derive from any UI base class
 * (ideally it should be a POCO). It should (of course!) be unit testable and be able to function without any UI at all.
 *
 * @see: https://blogs.msdn.microsoft.com/dphill/2009/01/31/the-viewmodel-pattern/
 */
abstract class BaseViewModel : ViewModel(), KoinComponent {

    private val dispatchers by inject<ProjectDispatchers>()

    fun async(block: suspend () -> Unit) {
        // The use of launch { } is enough for view models most times.
        // An advantage is in tests where we don't must use things like async { }.await to get exception reports.
        // https://stackoverflow.com/questions/46226518/what-is-the-difference-between-launch-join-and-async-await-in-kotlin-coroutines
        CoroutineScope(dispatchers.io).launch { block() }
    }
}
