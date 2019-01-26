package com.template.project.data.network.tools

import android.util.Log
import retrofit2.Response
import java.util.regex.Pattern

/**
 * Common class used by API responses.
 * @param <BodyType> the type of the response object
 *
 * Adjusted copy of google example Response which combines error/empty/success to avoid instance checks and casting.
</BodyType> */
class ApiResponse<BodyType>(
    var body: BodyType? = null,
    var linkHeader: String? = null,
    var errorMessage: String? = null,
    val isSuccessful: Boolean
) {

    private val links = linkHeader?.extractLinks() ?: emptyMap()

    companion object {
        fun <T> create(error: Throwable): ApiResponse<T> {
            return ApiResponse(errorMessage = error.message ?: "unknown error", isSuccessful = true)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiResponse(isSuccessful = true)
                } else {
                    ApiResponse(
                        body = body,
                        linkHeader = response.headers().get("link"),
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

    private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
    private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
    private val NEXT_LINK = "next"

    private fun String.extractLinks(): Map<String, String> {
        val links = mutableMapOf<String, String>()
        val matcher = LINK_PATTERN.matcher(this)

        while (matcher.find()) {
            val count = matcher.groupCount()
            if (count == 2) {
                links[matcher.group(2)] = matcher.group(1)
            }
        }
        return links
    }

    val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
        links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1))
                } catch (ex: NumberFormatException) {
                    Log.w("ApiResponse", "cannot parse next page from $next")
                    null
                }
            }
        }
    }
}