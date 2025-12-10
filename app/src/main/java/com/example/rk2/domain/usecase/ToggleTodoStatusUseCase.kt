package com.example.rk2.domain.usecase

import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.repository.TodoRepository
import javax.inject.Inject

/**
 * Use case for toggling the completion status of a todo item.
 * This encapsulates the business logic for toggling todo status.
 */
class ToggleTodoStatusUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    /**
     * Execute the use case to toggle the completion status of a todo.
     */
    suspend operator fun invoke(todo: TodoItem) {
        repository.toggleTodoStatus(todo)
    }
}

