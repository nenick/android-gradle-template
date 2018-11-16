package com.template.project.tools

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.template.project.ProjectNavigation
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EFragment
import org.koin.android.ext.android.inject
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

    protected val navigate: ProjectNavigation by inject()

    /** Activate the up navigation button on the Toolbar */
    protected fun showUpNavigation() {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}