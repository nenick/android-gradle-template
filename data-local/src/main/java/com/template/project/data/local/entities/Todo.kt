package com.template.project.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(

    @PrimaryKey val id: Int,

    val userId: Int,
    val title: String,
    val completed: Boolean
)