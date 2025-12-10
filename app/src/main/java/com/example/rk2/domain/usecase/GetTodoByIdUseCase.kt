package com.example.rk2.domain.usecase

import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.repository.TodoRepository
import javax.inject.Inject

/**
 * Use case for retrieving a single todo by ID.
 * This encapsulates the business logic for getting a todo by ID.
 */
class GetTodoByIdUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    /**
     * Execute the use case and return the todo item or null if not found.
     */
    suspend operator fun invoke(id: Int): TodoItem? {
        return repository.getTodoById(id)
    }
}

