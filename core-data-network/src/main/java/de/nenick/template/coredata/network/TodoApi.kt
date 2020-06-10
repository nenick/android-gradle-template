package de.nenick.template.coredata.network

import de.nenick.template.coredata.network.base.ApiResponse
import de.nenick.template.coredata.network.models.TodoJson
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class TodoApi(

    private val baseUrl: String
) {

    internal object Endpoint {
        //var baseUrl =
        fun todoList() = "todos"
        fun todoById(id: Int) = "todos/$id"
    }

    private val client = HttpClient {
        // When expectSuccess=true it turns to exception controlled flow when non 2XX result code.
        expectSuccess = false

        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    suspend fun todoList(): ApiResponse<List<TodoJson>, String> {
        val response = client.get<HttpResponse>("$baseUrl/${Endpoint.todoList()}")
        val content = response.receive<List<TodoJson>>()
        return ApiResponse.Success(content)
    }

    suspend fun todoById(id: Int): ApiResponse<TodoJson, String> {
        val response = client.get<HttpResponse>("$baseUrl/${Endpoint.todoById(id)}")
        return if (response.status == HttpStatusCode.OK) {
            ApiResponse.Success(response.receive())
        } else {
            ApiResponse.Error(response.receive())
        }
    }
}