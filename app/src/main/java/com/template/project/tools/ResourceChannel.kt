package com.template.project.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope

@ExperimentalCoroutinesApi
abstract class ResourceChannel<DataType>(
    private val producerScope: ProducerScope<DataType>
) {
    protected abstract suspend fun loadFromStorage(): LiveData<DataType>

    private val dataObserver = Observer<DataType> {
        CoroutineScope(producerScope.coroutineContext).async { producerScope.send(it) }
    }

    suspend fun startProcessing() {
        val storedData = loadFromStorage()
        CoroutineScope(Dispatchers.Main).launch { storedData.observeForever(dataObserver) }
        producerScope.invokeOnClose { storedData.removeObserver(dataObserver) }
    }
}