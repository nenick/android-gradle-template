package com.template.project._base

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

    final fun <T : ViewModel> provider(cls: Class<T>): ViewModelProvider<T> {
        val activity = this
        return object : ViewModelProvider<T>() {
            override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
                return ViewModelProviders.of(activity, viewModelFactory).get(cls)
            }
        }
    }

    abstract class ViewModelProvider<T: ViewModel> {
        abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    }
}