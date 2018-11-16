package com.template.project.model.repositories


import android.content.Context
import android.os.AsyncTask
import com.template.project.data.local.ProjectDatabase
import com.template.project.data.local.entities.Todo
import com.template.project.data.network.ApiBuilder
import com.template.project.tools.AsyncData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TodoRepository: KoinComponent {

    private val applicationContext: Context by inject()

    private val database = lazy { ProjectDatabase.getDatabase(applicationContext) }
    private val network = ApiBuilder().todoApi()

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

            if (database.value.todo().getAll().isEmpty()) {
                database.value.todo().insert(Todo(1, 1, "first", false))
                database.value.todo().insert(Todo(2, 1, "second", false))
                database.value.todo().insert(Todo(3, 1, "it", false))
                database.value.todo().insert(Todo(4, 1, "will", false))
                database.value.todo().insert(Todo(5, 1, "happen", false))
            }

            result.value = database.value.todo().getAll()

            val response = network.todos().execute()

            val content = response.body()!!
            database.value.beginTransaction()
            database.value.todo().deleteAll()
            content.forEach {
                database.value.todo().insert(
                    Todo(
                        it.id,
                        it.userId,
                        it.title,
                        it.completed
                    )
                )
            }
            database.value.endTransaction()

            result.value = database.value.todo().getAll()
        }

        return result
    }
}