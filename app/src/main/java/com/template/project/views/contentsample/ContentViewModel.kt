package com.template.project.views.contentsample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.template.project.data.local.entities.Todo
import com.template.project.model.repositories.TodoRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ContentViewModel : ViewModel(), KoinComponent {

    private val repository: TodoRepository by inject()

    private val todoList = MutableLiveData<List<Todo>>()

    fun observerTodo(owner: LifecycleOwner, observer: (List<Todo>) -> Unit) {
        todoList.observe(owner, Observer { observer(it) })
        repository.getTodos().observe {
            todoList.postValue(it)
        }
    }

}