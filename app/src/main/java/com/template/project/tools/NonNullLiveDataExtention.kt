package com.template.project.tools

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.observeNullSafe(this, body)
}

fun <T> LiveData<T>.observeNullSafe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer { it?.let(observer) })
}