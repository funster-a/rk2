package com.example.rk2.domain.usecase

import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.repository.TodoRepository
import javax.inject.Inject

/**
 * Use case for adding a new todo item.
 * This encapsulates the business logic for adding todos.
 */
class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    /**
     * Execute the use case to add a new todo.
     */
    suspend operator fun invoke(todo: TodoItem) {
        repository.insertTodo(todo)
    }
}

