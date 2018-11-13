package com.template.project.data.network

import com.template.project.data.network.entities.Todo
import retrofit2.Call
import retrofit2.http.GET


// https://jsonplaceholder.typicode.com/todos/1
interface TodoApi {

    @GET("/todos")
    fun todos(): Call<List<Todo>>
}