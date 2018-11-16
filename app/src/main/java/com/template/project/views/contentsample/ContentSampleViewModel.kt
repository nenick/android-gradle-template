package com.template.project.views.contentsample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.template.project.data.local.entities.Todo
import com.template.project.model.repositories.TodoRepository
import com.template.project.tools.BaseViewModel
import org.koin.standalone.inject

class ContentSampleViewModel : BaseViewModel() {

    private val repository: TodoRepository by inject()

    private val todoList = MutableLiveData<List<Todo>>()

    fun observerTodo(owner: LifecycleOwner, observer: (List<Todo>) -> Unit) {
        todoList.observe(owner, Observer { observer(it) })
        repository.getTodo().observe {
            todoList.postValue(it)
        }
    }

}