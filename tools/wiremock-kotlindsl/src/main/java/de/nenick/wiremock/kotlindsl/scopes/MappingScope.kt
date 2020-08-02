package de.nenick.wiremock.kotlindsl.scopes

class MappingScope {

    var priority = 100
    val url = UrlPatternScope()
    var willReturn = ResponseScope()

    fun willReturn(fn: ResponseScope.() -> Unit) {
        this.willReturn = ResponseScope().apply(fn)
    }
}