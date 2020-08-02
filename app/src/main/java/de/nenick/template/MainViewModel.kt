package de.nenick.template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.nenick.template.coredata.network.TodoApi
import de.nenick.template.coredata.network.models.ApiResponse
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val api by inject<TodoApi>()

    val result = MutableLiveData<String>()

    fun requestTodo() = viewModelScope.launch {
        val request = api.todoById(10)
        result.value = when (request) {
            is ApiResponse.Success -> request.content.title
            is ApiResponse.Error -> request.error
        }
    }
}