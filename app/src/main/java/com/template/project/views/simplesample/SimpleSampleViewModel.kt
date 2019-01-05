package com.template.project.views.simplesample

import androidx.lifecycle.MutableLiveData
import com.template.project.tools.BaseViewModel

class SimpleSampleViewModel : BaseViewModel() {

    val textInput = MutableLiveData<String>()
}