package de.nenick.template.coredata.network

import de.nenick.template.coredata.network.base.ApiCallResult
import de.nenick.template.coredata.network.base.ApiResponse
import de.nenick.template.coredata.network.models.TodoJson
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class TodoApi {

    private val client = HttpClient {
        // When expectSuccess=true it turns to exception controlled flow when non 2XX result code.
        expectSuccess = false

        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    suspend fun todoList(): ApiResponse<List<TodoJson>> {
        val response = client.get<HttpResponse>("https://jsonplaceholder.typicode.com/todos")
        val content = response.receive<List<TodoJson>>()
        return ApiResponse(content, ApiCallResult.Success)
    }

    suspend fun todo(id: Int): ApiResponse<TodoJson> {
        val response = client.get<HttpResponse>("https://jsonplaceholder.typicode.com/todos/$id")
        return if(response.status == HttpStatusCode.OK) {
            val content = response.receive<TodoJson>()
            ApiResponse(content, ApiCallResult.Success)
        } else {
            val content = response.receive<TodoJson>()
            ApiResponse(content, ApiCallResult.Error)
        }
    }
}