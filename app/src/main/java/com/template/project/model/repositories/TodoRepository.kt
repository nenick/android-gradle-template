package com.template.project.model.repositories

import com.template.project.data.local.TodoDao
import com.template.project.data.local.entities.Todo
import com.template.project.data.network.TodoApi
import com.template.project.data.network.entities.TodoJson
import com.template.project.data.network.tools.ApiResponse
import com.template.project.tools.BaseRepository
import com.template.project.tools.SyncResult
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TodoRepository : BaseRepository() {

    private val todoDao: TodoDao by inject()
    private val todoApi: TodoApi by inject()

    suspend fun readTodoList(): List<Todo> = read { todoDao.getAll() }

    suspend fun observeTodoList(): ReceiveChannel<List<Todo>> = observe { todoDao.getAllLive() }

    suspend fun fetchTodoList(): SyncResult {
        return if (readTodoList().isEmpty()) {
            fetch(todoApi.allTodo()) { response: List<TodoJson> ->
                todoDao.updateAll(response.map { Todo(it.id, it.userId, it.title, it.completed) })
            }
        } else {
            SyncResult.succeeded()
        }
    }
}