package de.nenick.template.coredata.network.models

data class TodoJson(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)