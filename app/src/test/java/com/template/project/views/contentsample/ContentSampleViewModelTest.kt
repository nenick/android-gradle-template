package com.template.project.views.contentsample

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.template.project.data.local.entities.Todo
import com.template.project.model.repositories.TodoRepository
import com.template.project.test.tools.ViewModelTestDefaults
import com.template.project.tools.SyncResult
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.mockito.Mockito.verify

class ContentSampleViewModelTest : ViewModelTestDefaults() {

    private val repositoryObserveTodoChannels = producerChannels<List<Todo>>()
    private val repository = mock<TodoRepository> {
        onBlocking { fetchTodoList(false) } doReturn SyncResult.succeeded()
        onBlocking { fetchTodoList(true) } doReturn SyncResult.succeeded()
        onBlocking { observeTodoList() } doReturn repositoryObserveTodoChannels.receiveChannel
    }

    init {
        StandAloneContext.startKoin(listOf(module {
            single { coroutineDispatcher }
            factory { repository }
        }))
    }

    private val model = ContentSampleViewModel()

    @Test
    fun `observe todo list on initialisation`() {
        runBlocking { verify(repository).observeTodoList() }
    }

    @Test
    fun `refresh todo list on initialisation (no force)`() {
        runBlocking { verify(repository).fetchTodoList(false) }
    }

    @Test
    fun `force refresh todo list when requested`() {
        model.refreshTodo()
        runBlocking { verify(repository).fetchTodoList(true) }
    }

    @Test
    fun `refresh state when refresh todo is requested`() {
        val refreshingStates = mutableListOf<Boolean>()
        observe(model.todoListIsLoading) { refreshingStates.add(it) }

        model.refreshTodo()

        val initial = false
        val refreshing = true
        val done = false
        assertThat(refreshingStates).containsExactlyElementsIn(listOf(initial, refreshing, done))
    }

    @Test
    fun `provide todo list results`() {
        repositoryObserveTodoChannels.send(emptyList())
        assertThat(model.todoList.value).isEmpty()

        repositoryObserveTodoChannels.send(listOf(Todo(1, 1, "TestTodo", true)))
        assertThat(model.todoList.value).isNotEmpty()
    }
}
