package com.template.project.views.contentsample

import androidx.lifecycle.MutableLiveData
import com.template.project.data.local.entities.Todo
import com.template.project.tools.BaseViewModel

class ContentDetailsViewModel : BaseViewModel() {

    fun load(itemId: Int) {
        // TODO load it through repository
        val item = Todo(itemId, 1, "for testing", false)

        todoItem.postValue(item)
    }

    val todoItem = MutableLiveData<Todo>()

}