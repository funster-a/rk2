package com.example.rk2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rk2.data.local.dao.TodoDao
import com.example.rk2.data.local.entity.TodoEntity

/**
 * Room Database class for the Todo application.
 * This is the main database access point.
 */
@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

