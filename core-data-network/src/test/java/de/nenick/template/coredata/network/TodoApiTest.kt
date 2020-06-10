package de.nenick.template.coredata.network

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import de.nenick.template.coredata.network.base.ApiResponse
import de.nenick.template.coredata.network.models.TodoJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA


class TodoApiTest {

    @get:Rule
    val wireMockRule: WireMockRule = WireMockRule()

    private val baseUrl = "http://localhost:8080"
    private val api = TodoApi(baseUrl)
    private val todoId = 1234

    @Test
    fun `todoById successful`() = runBlockingUnit {
        givenThat(TodoApiMockResponses.todoById)
        val result = api.todoById(todoId)
        expectThat(result).isA<ApiResponse.Success<TodoJson, String>>()
    }

    @Test
    fun `todoById not found`() = runBlockingUnit {
        givenThat(TodoApiMockResponses.todoByIdNotFound)
        val result = api.todoById(todoId)
        expectThat(result).isA<ApiResponse.Error<TodoJson, String>>()
    }

    @Test
    fun `todoList successful`() = runBlockingUnit {
        givenThat(TodoApiMockResponses.todoList)
        val result = TodoApi(baseUrl).todoList()
        expectThat(result).isA<ApiResponse.Success<TodoJson, String>>()
    }

    /** Avoid warnings about returning non Unit types. */
    private fun runBlockingUnit(block: suspend CoroutineScope.() -> Unit) = runBlocking {
        // TODO switch to the runBlockingTest { } when issues are fixed
        // this wrapper may then be eliminated
        // https://github.com/Kotlin/kotlinx.coroutines/issues/1222
        // https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test
        block()
    }

    object TodoApiMockResponses {
        val todoById = StubMapping.buildFrom(javaClass.classLoader.getResource("todoItem.json")!!.readText())
        val todoByIdNotFound = StubMapping.buildFrom(javaClass.classLoader.getResource("todoItemNotFound.json")!!.readText())
        val todoList = StubMapping.buildFrom(javaClass.classLoader.getResource("todoList.json")!!.readText())
    }
}

fun givenThat(mapping: StubMapping) {
    WireMock().register(mapping)
}