package com.template.project.test.tools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.template.project.tools.ProjectDispatchers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.ClassRule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext

abstract class ViewModelTestDefaults : KoinComponent, LifecycleOwner {

    companion object {

        /**
         * Avoid the RuntimeException: Method getMainLooper in android.os.Looper not mocked.
         *
         * As a static class rule we can safely call LiveData methods before @Before.
         */
        @ClassRule
        @JvmField
        var rule = InstantTaskExecutorRule()
    }

    /**
     * Exceptions in coroutines would be swallowed without custom handling.
     *
     * Situation:
     * Non suspend function spawns new coroutine with launch / async.
     * The Dispatchers.Unconfined reuse the current thread but when an exception occur it's still like an async thread.
     *
     * TODO Find a better solution to fail the test together with useful hints.
     */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Print the stacktrace to get a hint that something went wrong.
        exception.printStackTrace()

        // Forcing an early fail when next coroutine would be started.
        // This works since we using the main thread for executing coroutines.
        Thread.currentThread().interrupt()
    }

    val coroutineContext = Dispatchers.Unconfined + coroutineExceptionHandler
    val coroutineDispatcher = ProjectDispatchers(coroutineContext, coroutineContext)

    data class ProducerResult<ResultType>(
        val produceChannel: ProducerScope<ResultType>,
        val receiveChannel: ReceiveChannel<ResultType>
    ) {
        fun send(value: ResultType) {
            runBlocking { produceChannel.send(value) }
        }
    }

    fun <ResultType> producerChannels(): ProducerResult<ResultType> {
        var producer: ProducerScope<ResultType>? = null
        val receiver = CoroutineScope(coroutineDispatcher.io).produce<ResultType> {
            producer = this
            // Loop to keep the producer channel alive, or no more data will be received.
            while (isActive) delay(1000)
        }
        return ProducerResult(producer!!, receiver)
    }

    override fun getLifecycle() = LifecycleRegistry(mock()).apply { handleLifecycleEvent(Lifecycle.Event.ON_RESUME) }

    fun <DataType> observe(source: LiveData<DataType>, block: (value: DataType) -> Unit) {
        source.observe(this, Observer { block(it) })
    }

    @After
    fun cleanDefaults() {
        StandAloneContext.stopKoin()
    }
}
