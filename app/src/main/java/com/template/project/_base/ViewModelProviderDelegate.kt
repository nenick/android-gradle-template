package com.template.project._base

import androidx.lifecycle.ViewModel
import kotlin.reflect.KProperty

abstract class ViewModelProviderDelegate<T : ViewModel> {
    abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}