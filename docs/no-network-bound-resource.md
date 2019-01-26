# Don't use NetworkBoundResource

*Don't make the special case to the default one and make your life more easy.*

**Short:** It force you to complex code whe you want simple things.

This resource implementation always follow this logic:

`read local data -> check if should fetch new data -> report current local data -> does fetch new data -> store new data to local -> report new local data`

But what when you want to

* just call a remote resource and perform action based on it?
* just read local data and perform action based on it?

Instead of just doing it, you will implement workarounds, use complex async tools and must think about how it must be done.

However here an example how NetworkBoundResource does work with coroutines:

```
import com.template.project.data.network.tools.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

@ExperimentalCoroutinesApi
abstract class NetworkBoundResource<StorageResult, BackendResult>{

    private lateinit var producerScope: ProducerScope<Resource<StorageResult>>

    protected abstract fun loadFromStorage(): StorageResult
    protected abstract fun shouldFetchFromBackend(storedData: StorageResult): Boolean
    protected abstract suspend fun loadFromBackend(): ApiResponse<BackendResult>
    protected abstract fun storeBackendResult(receivedData: BackendResult)
    protected abstract fun readBackendError(backendResponse: ApiResponse<BackendResult>): String

    suspend fun startProcessing() {
        reportLoading()

        // first look for local stored content
        val storedData = loadFromStorage()

        // check if data should be updated
        if (shouldFetchFromBackend(storedData)) {
            reportLoading(storedData)

            // fetch new content from backend
            val backendResponse = loadFromBackend()
            if (backendResponse.isSuccessful) {

                // store if any content exists
                backendResponse.body?.let { storeBackendResult(it) }

                // reload stored content to have the latest one
                reportSuccess(loadFromStorage())
            } else {
                reportError(readBackendError(backendResponse))
            }
        } else {
            // report the current stored data
            reportSuccess(storedData)
        }

        // close channel, no more results will follow
        producerScope.close()
    }

    private suspend fun reportSuccess(data: StorageResult) {
        producerScope.send(Resource.success(data))
    }

    private suspend fun reportError(detail: String) {
        producerScope.send(Resource.error(detail))
    }

    private suspend fun reportLoading(data: StorageResult? = null) {
        producerScope.send(Resource.loading(data))
    }

    fun producer(producerScope: ProducerScope<Resource<StorageResult>>): NetworkBoundResource<StorageResult, BackendResult> {
        this.producerScope = producerScope
        return this
    }
}
```

```
class Resource<Data> private constructor(
    val isLoading: Boolean,
    val data: Data?,
    val error: String?
) {
    companion object {
        fun <Data> loading(data: Data? = null): Resource<Data> = Resource(true, data, null)
        fun <Data> success(data: Data? = null): Resource<Data> = Resource(true, data, null)
        fun <Data> error(error: String? = null): Resource<Data> = Resource(true, null, error)

    }
}
```

```
suspend fun loadTodoListResource(): ReceiveChannel<Resource<List<Todo>>> = produceResource {
    object : NetworkBoundResource<List<Todo>, List<TodoJson>>() {
        override fun loadFromStorage() = todoDao.getAll()
        override fun shouldFetchFromBackend(storedData: List<Todo>) = true
        override suspend fun loadFromBackend(): ApiResponse<List<TodoJson>> = todoApi.allTodo()
        override fun storeBackendResult(receivedData: List<TodoJson>) {
            todoDao.updateAll(receivedData.map { Todo(it.id, it.userId, it.title, it.completed) })
        }

        override fun readBackendError(backendResponse: ApiResponse<List<TodoJson>>) =
            backendResponse.errorMessage
                ?: backendResponse.body?.toString()
                ?: "undefined error"
    }
}
```