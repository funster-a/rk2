package com.example.rk2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rk2.domain.model.Priority

/**
 * Room Entity representing a Todo item in the database.
 * This is the data layer representation that maps to the database table.
 */
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: Long? = null,
    val isCompleted: Boolean = false,
    val categoryId: Int = 0
)

