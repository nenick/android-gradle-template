package com.template.project.model.repositories

import android.os.AsyncTask
import com.template.project.data.local.TodoDao
import com.template.project.data.local.entities.Todo
import com.template.project.data.network.TodoApi
import com.template.project.tools.AsyncData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TodoRepository : KoinComponent {

    private val todoDao: TodoDao by inject()
    private val todoApi: TodoApi by inject()

    /** Espresso wait automatically until all AsyncTask.THREAD_POOL_EXECUTOR threads are idle. */
    private val coroutineDispatcher = AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    private val getTodo = AsyncData<List<Todo>>(null)
    private var getTodoIsLoading = false

    fun getTodo(): AsyncData<List<Todo>> {
        if (!getTodoIsLoading) {
            getTodoIsLoading = true
            getTodoInternal().observe {
                getTodo.value = it
                getTodoIsLoading = false
            }
        }
        return getTodo
    }

    private fun getTodoInternal(): AsyncData<List<Todo>> {
        val result = AsyncData<List<Todo>>(null)


        CoroutineScope(coroutineDispatcher).launch {

            if (todoDao.getAll().isEmpty()) {
                todoDao.insert(Todo(1, 1, "first", false))
                todoDao.insert(Todo(2, 1, "second", false))
                todoDao.insert(Todo(3, 1, "it", false))
                todoDao.insert(Todo(4, 1, "will", false))
                todoDao.insert(Todo(5, 1, "happen", false))
            }
            result.value = todoDao.getAll()

            val response = todoApi.todos().execute()
            val content = response.body()!!

            todoDao.updateAll(content.map {
                Todo(
                    it.id,
                    it.userId,
                    it.title,
                    it.completed
                )
            })

            result.value = todoDao.getAll()
        }

        return result
    }
}