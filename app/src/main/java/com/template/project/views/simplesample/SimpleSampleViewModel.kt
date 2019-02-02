package com.template.project.views.simplesample

import androidx.lifecycle.MutableLiveData
import com.template.project.tools.BaseViewModel

open class SimpleSampleViewModel : BaseViewModel() {

    open val textInput = MutableLiveData<String>()
}
