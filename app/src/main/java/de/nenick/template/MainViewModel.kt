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

    fun loadTodo(id: Int) = viewModelScope.launch {
        result.value = "doing request"
        val request = api.todoById(id)
        result.value = when (request) {
            is ApiResponse.Success -> request.content.title
            is ApiResponse.Error -> request.error
        }
    }
}