package com.template.project.model.repositories

import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.*
import com.nhaarman.mockitokotlin2.*
import com.template.project.data.local.TodoDao
import com.template.project.data.local.entities.Todo
import com.template.project.data.network.TodoApi
import com.template.project.data.network.entities.TodoJson
import com.template.project.data.network.tools.ApiResponse
import com.template.project.test.tools.RepositoryTestDefaults
import com.template.project.tools.SyncResult
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

@InternalCoroutinesApi
class TodoRepositoryTest : RepositoryTestDefaults() {

    // Some common test data.
    private val emptyLocalData = emptyList<Todo>()
    private val someLocalData = listOf(Todo(1, 1, "TestTodo", true))

    private val someRemoteData = listOf(TodoJson(1, 1, "TestTodo", true))

    private val apiResponseSomeData = ApiResponse(someRemoteData, isSuccessful = true)
    private val apiResponseError = ApiResponse<List<TodoJson>>(isSuccessful = false, errorMessage = "TestError")

    private val syncSuccess = SyncResult.succeeded()
    private val syncError = SyncResult(isSuccess = false, errorDetail = "TestError")

    // Prepare common mocking.
    private val observableTodList = MutableLiveData<List<Todo>>()
    private val todoDao = mock<TodoDao> {
        // Always prepare early when method return LiveData to avoid strange errors.
        on { getAllLive() } doReturn observableTodList
    }

    private val todoApi = mock<TodoApi>()

    init {
        // Provide necessary dependencies for the unit under test.
        StandAloneContext.startKoin(listOf(module {
            factory { coroutineDispatcher }
            factory { todoDao }
            factory { todoApi }
        }))
    }

    // Create the unit under test after dependencies are prepared.
    private val repository = TodoRepository()

    /**
     * The under laying read {] logic is tested separately.
     *
     * This test has no real value, because it's mainly testing that another method is called.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `read local content`() {
        val data = emptyList<Todo>()
        whenever(todoDao.getAll()).thenReturn(data)

        val result = runBlocking { repository.readTodoList() }

        assertThat(result).isSameAs(data)
    }

    /**
     * The under laying observe {] logic is tested separately.
     *
     * This test has no real value, because it's mainly testing that another method is called.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `observe local content`() = runBlocking {
        val channel = repository.observeTodoList()

        observableTodList.value = emptyLocalData
        assertThat(channel.receive()).isSameAs(emptyLocalData)

        observableTodList.value = someLocalData
        assertThat(channel.receive()).isSameAs(someLocalData)
    }

    /**
     * The under laying fetch {] logic is tested separately.
     *
     * This test has no real value, because it's mainly testing that another method is called.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `fetch remote todo list`() = runBlocking {
        whenever(todoApi.allTodo()).thenReturn(apiResponseSomeData)

        val result = repository.fetchTodoList()

        verify(todoDao).updateAll(someLocalData)
        assertThat(result).isEqualTo(SyncResult.succeeded())
    }

    /**
     * Supporting conditional fetch logic.
     */
    @Test
    fun `fetch remote todo list - only if no local data available`() = runBlocking {
        whenever(todoDao.getAll()).thenReturn(someLocalData)

        val result = repository.fetchTodoList()

        verify(todoApi, never()).allTodo()
        assertThat(result).isEqualTo(syncSuccess)
    }

    /**
     * Supporting conditional fetch logic.
     */
    @Test
    fun `fetch remote todo list - forced`() = runBlocking {
        whenever(todoApi.allTodo()).thenReturn(apiResponseSomeData)

        val result = repository.fetchTodoList(true)

        verify(todoDao, never()).getAll()
        verify(todoDao).updateAll(someLocalData)
        assertThat(result).isEqualTo(SyncResult.succeeded())
    }

    /**
     * The under laying fetch {] logic is tested separately.
     *
     * This test has no real value, because it's mostly testing that another method is called.
     * This is more an example how this code will work and could be skipped.
     */
    @Test
    fun `fetch remote todo list - on failure`() = runBlocking {
        whenever(todoApi.allTodo()).thenReturn(apiResponseError)
        val result = repository.fetchTodoList()
        assertThat(result).isEqualTo(syncError)
    }
}
