# Coroutines

Coroutines are a lightweight threads and support to write async code which looks like procedural code. 
This avoids complicated callback spaghetti code.
https://developer.android.com/kotlin/coroutines

### ViewModelScope

`viewModelScope` is a predefined `CoroutineScope` that is included with the `ViewModel` KTX extensions. 
Note that all coroutines must run in a scope. A `CoroutineScope` manages one or more related coroutines.

When a coroutine is started with viewModelScope, it is executed in the scope of the ViewModel. 
If the ViewModel is destroyed because the user is navigating away from the screen, 
viewModelScope is automatically cancelled, and all running coroutines are canceled as well.

## Testing coroutines on Android

It is recommended to injecting Dispatchers into a Repository layer.
https://www.youtube.com/watch?v=KMb0Fs8rCRs