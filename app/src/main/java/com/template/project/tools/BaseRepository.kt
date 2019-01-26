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
    private val backgroundScope: CoroutineDispatcher by inject()

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

    protected suspend fun <ResponseType, ApiType : ApiResponse<ResponseType>> fetch(
        response: ApiType,
        storeData: (data: ResponseType) -> Unit
    ): SyncResult {
        return CoroutineScope(backgroundScope).async {
            if (response.isSuccessful) {
                storeData(response.body!!)
                SyncResult.succeeded()
            } else {
                SyncResult.failed(response)
            }
        }.await()
    }

    /**
     * Read from database.
     *
     * Wrapper to call database with coroutines scope.
     *
     * TODO: This could be replaced if Room natively supports coroutines.
     */
    protected suspend fun <DataType> read(loadData: () -> DataType): DataType {
        return CoroutineScope(backgroundScope).async { loadData() }.await()
    }

    /**
     * Observe database for changes.
     *
     * Wrapper for the Android LiveData to Kotlin coroutine ReceiveChannel.
     *
     * TODO: This could be replaced if Room natively supports coroutines.
     */
    @ExperimentalCoroutinesApi
    protected suspend fun <DataType> observe(loadData: () -> LiveData<DataType>): ReceiveChannel<DataType> {
        return CoroutineScope(backgroundScope).produce {
            object : ResourceChannel<DataType>(this) {
                override suspend fun loadFromStorage() = loadData()
            }.startProcessing()

            while (isActive) { delay(1000) }
        }
    }
}