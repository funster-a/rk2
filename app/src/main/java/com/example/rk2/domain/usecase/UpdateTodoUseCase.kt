package com.example.rk2.domain.usecase

import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.repository.TodoRepository
import javax.inject.Inject

/**
 * Use case for updating an existing todo item.
 * This encapsulates the business logic for updating todos.
 */
class UpdateTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    /**
     * Execute the use case to update a todo.
     */
    suspend operator fun invoke(todo: TodoItem) {
        repository.updateTodo(todo)
    }
}

