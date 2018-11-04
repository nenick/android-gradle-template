package com.template.project.tools

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity
import kotlin.reflect.KProperty

@EActivity
abstract class BaseActivity : AppCompatActivity() {

    @Bean
    protected lateinit var viewModelFactory: AndroidAnnotationViewModelFactory

    protected final fun <T : ViewModel> provider(cls: Class<T>): ViewModelProviderDelegate<T> {
        val base = this
        return object : ViewModelProviderDelegate<T>() {
            override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
                return ViewModelProviders.of(base, viewModelFactory).get(cls)
            }
        }
    }
}