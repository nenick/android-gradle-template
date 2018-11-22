package com.template.project.data.network

import com.template.project.data.network.entities.TodoJson
import retrofit2.Call
import retrofit2.http.GET

interface TodoApi {

    @GET("/todos")
    fun todos(): Call<List<TodoJson>>
}