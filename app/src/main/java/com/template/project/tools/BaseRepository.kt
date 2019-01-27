package com.template.project.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.template.project.data.network.tools.ApiResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.concurrent.ConcurrentHashMap

abstract class BaseRepository : KoinComponent {

    private val runningProcesses = ConcurrentHashMap<String, LiveData<*>>()
    private val dispatchers: ProjectDispatchers by inject()

    /**
     * Avoid that same action is executed parallel.
     *
     * Instead it returns the last provided LiveData which is connected to running process.
     *
     * @param identifier for matching same running processes
     * @param processDone when true this process will be removed from cache
     * @param process only executed if no cached process was found
     */
    protected fun <T> withProcessReuse(
        identifier: String,
        processDone: (data: T) -> Boolean,
        process: () -> LiveData<T>
    ): LiveData<T> {

        val processHolder = runningProcesses.getOrPut(identifier) {
            map(process()) { processResult ->
                if (processDone(processResult)) {
                    runningProcesses.remove(identifier)
                }
                processResult
            }
        }

        @Suppress("UNCHECKED_CAST")
        return processHolder as LiveData<T>
    }

    /**
     * Convenience method for fetching remote data and sync it locally.
     *
     * It respects the api response cases:
     *
     * a) Success with new data.
     * b) Success without content.
     * c) Error with details.
     */
    protected suspend fun <DataType, ApiType : ApiResponse<DataType>> fetch(
        response: ApiType,
        storeData: suspend (data: DataType) -> Unit
    ): SyncResult {
        return if (response.isSuccessful) {
            storeData(response.body!!)
            SyncResult.succeeded()
        } else {
            SyncResult.failed(response)
        }
    }

    /**
     * Convenience method for reading data synchronously.
     *
     * Wraps forced non main thread loading to Kotlin coroutines.
     *
     * TODO: This could be replaced if Room natively supports coroutines.
     */
    protected suspend fun <DataType> read(loadData: suspend () -> DataType): DataType {
        return runBlocking { loadData() }
    }

    /**
     * Convenience method for observing a data resource for changes.
     *
     * Wraps the Android LiveData events to Kotlin coroutines ReceiveChannel.
     *
     * TODO: This could be replaced if Room natively supports coroutines.
     */
    @ExperimentalCoroutinesApi
    protected suspend fun <DataType> observe(loadResource: () -> LiveData<DataType>): ReceiveChannel<DataType> {
        return CoroutineScope(dispatchers.io).produce {

            // Create a channel for receiving resource updates.
            ResourceChannel(this, loadResource).startProcessing()

            // Loop to keep the producer channel alive, or no more data will be received.
            while (isActive) delay(1000)
        }
    }
}