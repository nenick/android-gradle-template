package com.template.project.views.simplesample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class SimpleSampleViewModel : ViewModel() {

    private val lastTextInput = MutableLiveData<String>()

    fun observeTextInput(owner: LifecycleOwner, observer: Observer<in String>) {
        lastTextInput.observe(owner, observer)
    }

    fun updateTextInput(text: String) {
        lastTextInput.value = text
    }
}