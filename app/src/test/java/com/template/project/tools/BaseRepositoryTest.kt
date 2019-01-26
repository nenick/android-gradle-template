package com.template.project.tools

import androidx.lifecycle.*
import com.google.common.truth.Truth.assertThat
import com.template.project.test.tools.RepositoryTestDefaults
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Test
import org.mockito.Mockito.mock

@InternalCoroutinesApi
class BaseRepositoryTest : RepositoryTestDefaults() {

    private val activeOwner = {
        LifecycleRegistry(mock(LifecycleOwner::class.java)).apply {
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    private val repository = TestRepository()

    private val testIdentifier = "TestAction"
    private val alwaysDoneCondition: (String) -> Boolean = { true }
    private val testProcessData by lazy { MutableLiveData<String>().apply { value = "TestResult" } }

    // TODO add tests for read, observe, fetch!

    // TODO is reuse functionality still necessary?

    @Test
    fun `reuse running process instead spawning new one parallel`() {
        val processA = repository.processReuse(testIdentifier, alwaysDoneCondition) { testProcessData }
        val processB = repository.processReuse(testIdentifier, alwaysDoneCondition) { testProcessData }

        assertThat(processA).isSameAs(processB)
    }

    @Test
    fun `distinguish process types`() {
        val processA = repository.processReuse(testIdentifier, alwaysDoneCondition) { testProcessData }
        val processB = repository.processReuse("different", alwaysDoneCondition) { testProcessData }

        assertThat(processA).isNotSameAs(processB)
    }

    @Test
    fun `start new process when last one is already finished`() {
        val processA = repository.processReuse(testIdentifier, alwaysDoneCondition) { testProcessData }
        processA.observe(activeOwner) {}
        val processB = repository.processReuse(testIdentifier, alwaysDoneCondition) { testProcessData }

        assertThat(processA).isNotSameAs(processB)
    }


    // make protected functions visible for testing
    class TestRepository : BaseRepository() {
        fun <T> processReuse(
            identifier: String,
            doneCondition: (data: T) -> Boolean,
            process: () -> LiveData<T>
        ): LiveData<T> {
            return withProcessReuse(identifier, doneCondition, process)
        }
    }
}

