package com.example.rk2.domain.usecase

import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.repository.TodoRepository
import javax.inject.Inject

/**
 * Use case for deleting a todo item.
 * This encapsulates the business logic for deleting todos.
 */
class DeleteTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    /**
     * Execute the use case to delete a todo.
     */
    suspend operator fun invoke(todo: TodoItem) {
        repository.deleteTodo(todo)
    }
}

