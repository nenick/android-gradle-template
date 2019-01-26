package com.template.project.tools

import com.template.project.data.network.tools.ApiResponse

/** Report about network loading process. */
data class SyncResult(val isSuccess: Boolean, val errorDetail: String) {
    companion object {

        /** Convenient builder for success state. */
        fun succeeded() = SyncResult(true, "")

        /** Convenient builder for failure state. */
        fun failed(response: ApiResponse<*>) = SyncResult(false, errorDetails(response))

        private fun errorDetails(response: ApiResponse<*>) =
            response.errorMessage ?: "undefined error"
    }
}