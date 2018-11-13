package com.template.project.data.network

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.template.project.data.network.entities.Todo
import org.junit.Test

class TodoApiTest {

    private val api = ApiBuilder().todoApi()

    @Test
    fun `map Todo json`() {
        val result = Gson().fromJson(
            """
        {
            "userId": 10,
            "id": 198,
            "title": "quis eius est sint explicabo",
            "completed": true
        }
        """, Todo::class.java
        )

        assertThat(result).isEqualTo(
            Todo(
                198,
                10,
                "quis eius est sint explicabo",
                true
            )
        )
    }

    @Test
    fun `fetch ToDo list`() {
        val result = api.todos().execute()
        assertThat(result.isSuccessful).isTrue()

        val content = result.body()
        assertThat(content).hasSize(200)
    }
}