package com.template.project.views.contentsample

import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.template.project.data.local.entities.Todo
import com.template.project.model.repositories.TodoRepository
import com.template.project.test.tools.ViewModelTestDefaults
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.produce
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.mockito.Mockito.verify

@InternalCoroutinesApi
class ContentSampleViewModelTest : ViewModelTestDefaults() {

    private val repository = mock<TodoRepository>()
    private val repositoryTodoResult = MutableLiveData<List<Todo>>()

    private lateinit var producer: ProducerScope<List<Todo>>

    private val repRes = CoroutineScope(coroutineDispatcher.io).produce<List<Todo>> {
        producer = this
        // Loop to keep the producer channel alive, or no more data will be received.
        while (isActive) delay(1000)
    }.apply {
        runBlocking { whenever(repository.observeTodoList()).thenReturn(this@apply) }
    }

    init {
        StandAloneContext.startKoin(listOf(module {
            single { coroutineDispatcher }
            factory { repository }
        }))
    }

    private val model by lazy { ContentSampleViewModel() }

    @Before
    fun setup() {
        model // just initialise it (now we have the test rule executor scope available and it is save)
        repositoryTodoResult.observeForever { runBlocking { producer.send(it) } }
    }

    @Test
    fun `load todo list on initialisation`() {
        runBlocking { verify(repository).observeTodoList() }
        runBlocking { verify(repository).fetchTodoList() }
    }

    @Test
    fun `provide todo list result`() {
        repositoryTodoResult.value = emptyList()
        assertThat(model.todoList.value).isEmpty()

        repositoryTodoResult.value = listOf(Todo(1, 1, "TestTodo", true))
        assertThat(model.todoList.value).isNotEmpty()
    }
}