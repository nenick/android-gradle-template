package de.nenick.template.coredata.network.base

class ApiResponse<T>(
    val content: T,
    val status: ApiCallResult
)