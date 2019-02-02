package com.template.project.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Convenience class for mapping LiveData to a consumable coroutine channel.
 */
@ExperimentalCoroutinesApi
class ResourceChannel<DataType>(
    private val producerScope: ProducerScope<DataType>,
    private val loadResource: () -> LiveData<DataType>
) : KoinComponent {

    private val dispatchers by inject<ProjectDispatchers>()
    private val resourceConsumer = Observer<DataType> { sendToChannel(it) }

    fun startProcessing() {
        val resource = loadResource()

        mainThread { resource.observeForever(resourceConsumer) }

        producerScope.invokeOnClose {
            mainThread { resource.removeObserver(resourceConsumer) }
        }
    }

    private fun sendToChannel(it: DataType) {
        CoroutineScope(producerScope.coroutineContext).async { producerScope.send(it) }
    }

    private fun mainThread(block: () -> Unit) {
        CoroutineScope(dispatchers.main).launch { block() }
    }
}
