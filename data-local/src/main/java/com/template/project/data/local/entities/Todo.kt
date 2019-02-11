package com.template.project.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(

    @PrimaryKey val id: Int,

    val userId: Int,
    val title: String,
    val description: String,
    val completed: Boolean
) {
    fun titleValidation(): TitleValidation {
        return TitleValidation.Valid
    }

    fun descriptionValidation(): DescriptionValidation {
        return DescriptionValidation.Valid
    }
}

sealed class TitleValidation {
    object Valid : TitleValidation()
    object Empty : TitleValidation()
    object ToLong : TitleValidation() { const val maxCharacters = 100 }
}


sealed class DescriptionValidation {
    object Valid : DescriptionValidation()
    // Empty is allowed here
    object ToLong : DescriptionValidation() { const val maxCharacters = 1000 }
}
