package com.template.project.tools

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EFragment
import kotlin.reflect.KProperty

/** Provides some basic feature for working with Fragments. */
@EFragment
abstract class BaseFragment : Fragment() {

    @Bean
    protected lateinit var viewModelFactory: AndroidAnnotationViewModelFactory

    /** Provider for creating ViewModel. */
    protected final fun <T : ViewModel> provider(cls: Class<T>): ViewModelProviderDelegate<T> =
        object : ViewModelProviderDelegate<T>() {
            override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
                return ViewModelProviders.of(this@BaseFragment, viewModelFactory).get(cls)
            }
        }

    /** Activate the up navigation button on the Toolbar */
    protected fun showUpNavigation() {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    protected final fun navigate(@IdRes actionId: Int) {
        findNavController().navigate(actionId)
    }
}