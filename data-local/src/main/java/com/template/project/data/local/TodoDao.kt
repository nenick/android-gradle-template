package com.template.project.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.template.project.data.local.entities.Todo

@Dao
interface TodoDao {

    @Query("SELECT * from Todo")
    fun getAll(): List<Todo>

    @Query("SELECT * from Todo")
    fun getAllLive(): LiveData<List<Todo>>

    @Insert
    fun insert(data: Todo)

    @Query("DELETE FROM Todo")
    fun deleteAll()
}