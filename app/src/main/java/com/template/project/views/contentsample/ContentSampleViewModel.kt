package com.template.project.views.contentsample

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.template.project.data.local.entities.Todo
import com.template.project.model.repositories.TodoRepository
import com.template.project.tools.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import org.koin.standalone.inject

class ContentSampleViewModel : BaseViewModel() {

    private val repository: TodoRepository by inject()

    val errorChannel = MutableLiveData<String>()

    val todoList = MediatorLiveData<List<Todo>>()
    val todoListIsLoading = MutableLiveData<Boolean>()

    init {
        async { repository.observeTodoList().consumeEach { todoList.postValue(it) } }
        refreshTodo(false)
    }

    fun refreshTodo(forced: Boolean = true) = async {
        todoListIsLoading.postValue(true)
        repository.fetchTodoList(forced).apply {
            if (!isSuccess) errorChannel.postValue(errorDetail)
        }
        todoListIsLoading.postValue(false)
    }
}