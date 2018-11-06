package com.template.datanetwork

import com.template.datanetwork.entities.Todo
import retrofit2.Call
import retrofit2.http.GET


// https://jsonplaceholder.typicode.com/todos/1
interface StaticDataApi {

    @GET("/todos")
    fun todos(): Call<List<Todo>>
}