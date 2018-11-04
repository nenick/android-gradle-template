package com.template.datalocal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.template.datalocal.entities.Todo

@Dao
interface TodoDao {

    @Query("SELECT * from Todo")
    fun getAll(): List<Todo>

    @Insert
    fun insert(data: Todo)
}