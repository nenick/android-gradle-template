package com.template.project.data.network.tools

import retrofit2.Response

/**
 * Common class used by API responses.
 * @param <BodyType> the type of the response object
 *
 * Adjusted copy of google example Response which combines error/empty/success to avoid instance checks and casting.
</BodyType> */
class ApiResponse<BodyType>(
    var body: BodyType? = null,
    var errorMessage: String? = null,
    val isSuccessful: Boolean
) {

    companion object {

        private const val httpStatusNoContent = 204

        fun <T> create(error: Throwable): ApiResponse<T> {
            return ApiResponse(errorMessage = error.message ?: "unknown error", isSuccessful = true)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == httpStatusNoContent) {
                    ApiResponse(isSuccessful = true)
                } else {
                    ApiResponse(
                        body = body,
                        isSuccessful = true
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty() || msg == "{}") {
                    response.message()
                } else {
                    msg
                }
                ApiResponse(errorMessage = errorMsg ?: "unknown error", isSuccessful = false)
            }
        }
    }
}
