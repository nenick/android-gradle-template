package com.template.project.data.network

import com.google.common.truth.Truth.assertThat
import com.template.project.data.network.tools.ApiTest
import org.junit.Test
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TodoApiTest : ApiTest(), KoinComponent {

    private val api: TodoApi by inject()

    @Test
    fun `fetch ToDo list`() {
        val result = api.todos().execute()
        assertThat(result.isSuccessful).isTrue()

        val content = result.body()
        assertThat(content).hasSize(200)
    }
}