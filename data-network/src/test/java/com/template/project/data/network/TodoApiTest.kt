package com.template.project.data.network

import com.google.common.truth.Truth.assertThat
import com.template.project.data.network.tools.ApiBuilder
import com.template.project.data.network.tools.ApiTest
import org.junit.Test

class TodoApiTest : ApiTest() {

    private val api = ApiBuilder().todoApi()

    @Test
    fun `fetch ToDo list`() {
        val result = api.todos().execute()
        assertThat(result.isSuccessful).isTrue()

        val content = result.body()
        assertThat(content).hasSize(200)
    }
}