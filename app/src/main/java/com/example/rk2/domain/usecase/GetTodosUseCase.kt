package com.example.rk2.domain.usecase

import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving all todos.
 * This encapsulates the business logic for getting todos.
 */
class GetTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    /**
     * Execute the use case and return a Flow of all todos.
     */
    operator fun invoke(): Flow<List<TodoItem>> = repository.getAllTodos()
}

