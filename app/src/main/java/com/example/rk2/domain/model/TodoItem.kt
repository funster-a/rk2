package com.example.rk2.domain.model

/**
 * Domain model representing a Todo item.
 * This is the core business entity used throughout the app.
 */
data class TodoItem(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: Long? = null,
    val isCompleted: Boolean = false,
    val categoryId: Int = 0
)

/**
 * Priority levels for Todo items.
 */
enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

