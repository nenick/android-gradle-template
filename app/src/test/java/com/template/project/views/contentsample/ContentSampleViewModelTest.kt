package com.template.project.views.contentsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.template.project.data.local.entities.Todo
import com.template.project.model.repositories.TodoRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ChannelIterator
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.mockito.Mockito
import kotlin.coroutines.CoroutineContext

@InternalCoroutinesApi
class ContentSampleViewModelTest : KoinComponent {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val lifecycleOwner = {
        LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java)).apply {
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    private val repository = mock<TodoRepository>()
    private val repositoryTodoResult = MutableLiveData<List<Todo>>()

    private val model by lazy { ContentSampleViewModel() }

    private val repRes = mock<ReceiveChannel<List<Todo>>>()

    init {
        //whenever(repository.loadTodoListResource()).thenReturn(repRes)
        val mockIte = mock<ChannelIterator<List<Todo>>> {
            //onBlocking { hasNext() } doReturn false
        }
        whenever(repRes.iterator()).thenReturn(mockIte)

        StandAloneContext.startKoin(listOf(module {
            single { TestCoroutineContext() }
            single { get<TestCoroutineContext>() as CoroutineContext }
            factory { repository }
        }))

        model // just trigger lazy initialisation
    }

    val coroutineContext by inject<TestCoroutineContext>()

    @Test
    fun `load todo list on initialisation`() {
        model.todoList
        coroutineContext.triggerActions()

        val ex = coroutineContext.exceptions
        //verify(repository).loadTodoListResource()
    }

    @Test
    fun `provide todo list result`() {
        repositoryTodoResult.value = emptyList()
        assertThat(model.todoList.value).isNotNull()
    }

    @After
    fun clean() {
        StandAloneContext.stopKoin()
    }
}