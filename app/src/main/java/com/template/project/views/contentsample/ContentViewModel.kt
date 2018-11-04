package com.template.project.views.contentsample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.template.datalocal.entities.Todo
import com.template.project.model.repositories.TodoRepository
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean

@EBean
class ContentViewModel: ViewModel() {

    @Bean
    protected lateinit var repository: TodoRepository

    private val todoList = MutableLiveData<List<Todo>>()

    fun observerTodos(owner: LifecycleOwner, observer: (List<Todo>) -> Unit) {
        todoList.observe(owner, Observer { observer(it) })
        repository.getTodos().observe {
            todoList.postValue(it)
        }
    }

}