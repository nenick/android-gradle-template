package de.nenick.template.coredata.network

import de.nenick.template.coredata.network.base.ApiCallResult
import kotlinx.coroutines.*
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

class TodoApiTest {

    @Test
    fun `do a api call`() = runBlockingUnit {
        val result = TodoApi().todoList()
        expectThat(result.content).isNotEmpty()
    }

    @Test
    fun `do a api call success`() = runBlockingUnit {
        val result = TodoApi().todo(1)
        expectThat(result.status).isEqualTo(ApiCallResult.Success)
        expectThat(result.content.id).isEqualTo(1)
    }

    @Test
    fun `do a api call error`() = runBlockingUnit {
        val result = TodoApi().todo(-1)
        expectThat(result.status).isEqualTo(ApiCallResult.Error)
    }

    /** Avoid warnings about returning non Unit types. */
    private fun runBlockingUnit(block: suspend CoroutineScope.() -> Unit) = runBlocking {
        // TODO switch to the runBlockingTest { } when issues are fixed
        // https://github.com/Kotlin/kotlinx.coroutines/issues/1222
        // https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test
        block()
    }
}