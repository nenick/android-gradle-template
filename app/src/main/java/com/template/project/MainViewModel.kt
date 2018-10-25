package com.template.project

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.androidannotations.annotations.EBean

@EBean
class MainViewModel : ViewModel() {

    private val lastTextInput = MutableLiveData<String>()

    fun observeTextInput(owner: LifecycleOwner, observer: Observer<in String>) {
        lastTextInput.observe(owner, observer)
    }

    fun updateTextInput(text: String) {
        lastTextInput.value = text
    }
}