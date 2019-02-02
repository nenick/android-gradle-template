package com.template.project.data.network.entities

data class TodoJson(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)
