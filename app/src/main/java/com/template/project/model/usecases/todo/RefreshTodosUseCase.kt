package com.template.project.model.usecases.todo

import com.template.project.tools.AsyncData

class RefreshTodosUseCase {

    data class RefreshAnyDataResult(val state: String)

    fun apply(): AsyncData<RefreshAnyDataResult> {
        val result = AsyncData(RefreshAnyDataResult("Processing"))
        applyWith(result)
        return result
    }

    private fun applyWith(result: AsyncData<RefreshAnyDataResult>) {
        result.value = RefreshAnyDataResult("Finished")
    }
}

