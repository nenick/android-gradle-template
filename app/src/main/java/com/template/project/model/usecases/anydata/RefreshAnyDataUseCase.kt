package com.template.project.model.usecases.anydata

import com.template.project.tools.AsyncData

class RefreshAnyDataUseCase {

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

