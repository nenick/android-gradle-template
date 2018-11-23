package com.template.project.views.contentsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.template.project.data.local.entities.Todo
import com.template.project.model.repositories.TodoRepository
import com.template.project.tools.BaseViewModel
import org.koin.standalone.inject

class ContentSampleViewModel : BaseViewModel() {

    // A repository can be the single source of truth for accessing application data.
    // It will hiding the detail if data comes from local or remote storage.
    // Makes implementation and testing more easy.
    private val repository: TodoRepository by inject()

    fun todo(): LiveData<List<Todo>> {
        // Just return repository.getTodo() would be enough.
        // It's an example how you could access and transform the result before the view is getting it.
        // You can also return a different result type than which is delivered from repository.
        return map(repository.getTodo()) { todo ->
            todo.map { it.copy(title = it.title + " modified") }
        }
    }
}