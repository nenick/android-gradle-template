package com.template.project.data.network

import com.google.common.truth.Truth.assertThat
import com.template.project.data.network.entities.TodoJson
import com.template.project.data.network.tools.ApiTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.standalone.inject

class TodoApiTest : ApiTest() {

    private val api: TodoApi by inject()

    private val testTodo = 1
    private val testTodoNotExist = 201

    @Test
    fun `fetch ToDo list`() {
        val result = runBlocking { api.allTodo() }
        assertThat(result.body).hasSize(200)
    }

    @Test
    fun `fetch ToDo`() {
        val result = runBlocking { api.todo(testTodo) }
        assertThat(result.body).isEqualTo(TodoJson(1, 1, "delectus aut autem", false))
    }

    @Test
    fun `fetch ToDo - on failure`() {
        val result = runBlocking { api.todo(testTodoNotExist) }
        assertThat(result.errorMessage).isEqualTo("Not Found")
    }
}