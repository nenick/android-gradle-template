package de.nenick.template.coredata.network.endpoints

object TodoEndpoint {
    fun list() = "/todos"
    fun byId(id: Int) = "/todos/$id"
    fun byId(id: String) = "/todos/$id"
}