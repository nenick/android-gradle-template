package com.template.project.data.network

import com.template.project.data.network.entities.TodoJson
import com.template.project.data.network.tools.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoApi {

    @GET("/todos")
    suspend fun allTodo(): ApiResponse<List<TodoJson>>

    @GET("/todos/{id}")
    suspend fun todo(@Path("id") id: Int): ApiResponse<TodoJson>
}
