package de.nenick.template.coredata.network

import de.nenick.template.coredata.network.models.ApiResponse
import de.nenick.template.coredata.network.endpoints.TodoEndpoint
import de.nenick.template.coredata.network.models.TodoJson
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import org.koin.core.KoinComponent
import org.koin.core.inject

class TodoApi(
    private val baseUrl: String
) : KoinComponent {

    private val client by inject<HttpClient>()

    suspend fun todoList(): ApiResponse<List<TodoJson>, String> {
        val response = client.get<HttpResponse>("$baseUrl${TodoEndpoint.list()}")
        val content = response.receive<List<TodoJson>>()
        return ApiResponse.Success(content)
    }

    suspend fun todoById(id: Int): ApiResponse<TodoJson, String> {
        val response = client.get<HttpResponse>("$baseUrl${TodoEndpoint.byId(id)}")
        return if (response.status == HttpStatusCode.OK) {
            ApiResponse.Success(response.receive())
        } else {
            ApiResponse.Error(response.receive())
        }
    }
}