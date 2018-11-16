package com.template.project.model.repositories

import android.os.AsyncTask
import com.template.project.data.local.ProjectDatabase
import com.template.project.data.local.entities.Todo
import com.template.project.data.network.TodoApi
import com.template.project.tools.AsyncData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TodoRepository: KoinComponent {

    private val database: ProjectDatabase by inject()
    private val network: TodoApi by inject()

    /** Espresso wait automatically until all AsyncTask.THREAD_POOL_EXECUTOR threads are idle. */
    private val coroutineDispatcher = AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    private val getTodo = AsyncData<List<Todo>>(null)
    private var getTodoIsLoading = false

    fun getTodo(): AsyncData<List<Todo>> {
        if(!getTodoIsLoading) {
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

            if (database.todo().getAll().isEmpty()) {
                database.todo().insert(Todo(1, 1, "first", false))
                database.todo().insert(Todo(2, 1, "second", false))
                database.todo().insert(Todo(3, 1, "it", false))
                database.todo().insert(Todo(4, 1, "will", false))
                database.todo().insert(Todo(5, 1, "happen", false))
            }

            result.value = database.todo().getAll()

            val response = network.todos().execute()

            val content = response.body()!!
            database.beginTransaction()
            database.todo().deleteAll()
            content.forEach {
                database.todo().insert(
                    Todo(
                        it.id,
                        it.userId,
                        it.title,
                        it.completed
                    )
                )
            }
            database.endTransaction()

            result.value = database.todo().getAll()
        }

        return result
    }
}