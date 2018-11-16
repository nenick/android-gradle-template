package com.template.project.views.contentsample

import androidx.lifecycle.MutableLiveData
import com.template.project.data.local.entities.Todo
import com.template.project.tools.BaseViewModel

class ContentDetailsViewModel : BaseViewModel() {

    fun load(itemId: Int) {
        todoItem.postValue(Todo(itemId, 1, "for testing", false))
    }

    val todoItem = MutableLiveData<Todo>()

}