package de.nenick.template.coredata.network.base

sealed class ApiCallResult {
    object Error : ApiCallResult()
    object Success : ApiCallResult()
}