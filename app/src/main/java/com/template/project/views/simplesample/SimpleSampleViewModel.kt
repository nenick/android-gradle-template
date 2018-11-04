package com.template.project.views.simplesample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.template.project.model.AnyUsefulDependency
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean

@EBean
class SimpleSampleViewModel : ViewModel() {

    private val lastTextInput = MutableLiveData<String>()

    @Bean
    protected lateinit var anything: AnyUsefulDependency

    fun observeTextInput(owner: LifecycleOwner, observer: Observer<in String>) {
        lastTextInput.observe(owner, observer)
    }

    fun updateTextInput(text: String) {
        lastTextInput.value = text
    }
}