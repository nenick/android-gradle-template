package com.template.project.tools

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext

/**
 * Create instances of enhanced ViewModels with AndroidAnnotations (mainly for dependency injection feature).
 *
 * Enhanced classes hasn't default constructor.
 * That's how most dependency injection tools work when using constructor injection.
 */
// The singleton scope force usage of application context instead of activity context.
// This avoid leaking activity in memory and to the ViewModels dependencies.
@EBean(scope = EBean.Scope.Singleton)
class AndroidAnnotationViewModelFactory : ViewModelProvider.Factory {

    @RootContext
    protected lateinit var applicationContext: Context

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // use the enhanced version of this ViewModel class
        val enhancedClass = modelClass.classLoader!!.loadClass("${modelClass.name}_")

        // call the factory method to create a new instance
        val instanceCreator = enhancedClass.getMethod("getInstance_", Context::class.java)
        val instance = instanceCreator.invoke(null, applicationContext)

        @Suppress("UNCHECKED_CAST")
        return instance as T
    }
}

