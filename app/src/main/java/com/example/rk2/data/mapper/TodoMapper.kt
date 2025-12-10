package com.example.rk2.data.mapper

import com.example.rk2.data.local.entity.TodoEntity
import com.example.rk2.domain.model.TodoItem

/**
 * Mapper utility to convert between Domain and Data layer models.
 */
object TodoMapper {
    /**
     * Convert a TodoEntity (Data layer) to TodoItem (Domain layer).
     */
    fun TodoEntity.toDomain(): TodoItem {
        return TodoItem(
            id = id,
            title = title,
            description = description,
            priority = priority,
            dueDate = dueDate,
            isCompleted = isCompleted,
            categoryId = categoryId
        )
    }
    
    /**
     * Convert a TodoItem (Domain layer) to TodoEntity (Data layer).
     */
    fun TodoItem.toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            description = description,
            priority = priority,
            dueDate = dueDate,
            isCompleted = isCompleted,
            categoryId = categoryId
        )
    }
}

