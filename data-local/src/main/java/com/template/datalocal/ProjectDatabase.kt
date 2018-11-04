package com.template.datalocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.template.datalocal.entities.Todo

@Database(entities = [Todo::class], version = 1)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun todo(): TodoDao

    companion object {

        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getDatabase(context: Context): ProjectDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, ProjectDatabase::class.java,
                    "project_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}