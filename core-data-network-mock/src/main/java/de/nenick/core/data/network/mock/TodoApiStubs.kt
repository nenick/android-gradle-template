package de.nenick.core.data.network.mock

import de.nenick.core.data.network.mock.TodoTestData.task1NotCompleted
import de.nenick.core.data.network.mock.TodoTestData.task2NotCompleted
import de.nenick.core.data.network.mock.TodoTestData.task3Completed
import de.nenick.core.data.network.mock.TodoTestData.task4Completed
import de.nenick.template.coredata.network.endpoints.TodoEndpoint
import de.nenick.wiremock.kotlindsl.WireMockStub.get

object TodoApiStubs {

    object EmptyResponse

    private const val matchAnyId = "[0-9]+"

    private val defaultJsonResponseHeaders = mapOf("Content-Type" to "application/json")

    val list = get {
        url matching TodoEndpoint.list()
        willReturn {
            headers = defaultJsonResponseHeaders
            body jsonFromObject listOf(
                task1NotCompleted,
                task2NotCompleted,
                task3Completed,
                task4Completed
            )
        }
    }

    val byId = get {
        url matching TodoEndpoint.byId(matchAnyId)
        willReturn {
            headers = defaultJsonResponseHeaders
            body jsonFromObject task4Completed
        }
    }

    val byIdNotFound = get {
        priority = 1
        url matching TodoEndpoint.byId(0)
        willReturn {
            status = 404
            body jsonFromObject EmptyResponse
        }
    }
}