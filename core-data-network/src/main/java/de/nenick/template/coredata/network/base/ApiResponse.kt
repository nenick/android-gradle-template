package de.nenick.template.coredata.network.base

sealed class ApiResponse<CONTENT, ERROR> {

    class Success<CONTENT, ERROR>(
        val content: CONTENT
    ) : ApiResponse<CONTENT, ERROR>()

    class Error<CONTENT, ERROR>(
        val error: ERROR
    ) : ApiResponse<CONTENT, ERROR>()
}