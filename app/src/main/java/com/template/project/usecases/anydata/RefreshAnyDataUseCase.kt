package com.template.project.usecases.anydata

import com.template.project._base.AsyncData

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

