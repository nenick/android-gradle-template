package de.nenick.wiremock.kotlindsl.scopes

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.common.Json

class ResponseBodyScope(
    val stringHandler: (bodyString: String) -> ResponseDefinitionBuilder,
    val fileHandler: (contents: String) -> ResponseDefinitionBuilder) {

    infix fun jsonFromObject(obj: Any) = stringHandler(Json.write(obj))
    infix fun string(string: String) = stringHandler(string)
    infix fun file(path: String) = fileHandler(path)
}