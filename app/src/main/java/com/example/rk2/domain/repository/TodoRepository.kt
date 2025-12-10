package com.example.rk2.domain.repository

import com.example.rk2.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface defining the contract for Todo data operations.
 * This is part of the Domain layer and should not depend on implementation details.
 */
interface TodoRepository {
    /**
     * Get all todos as a Flow for reactive updates.
     */
    fun getAllTodos(): Flow<List<TodoItem>>
    
    /**
     * Get a single todo by ID.
     */
    suspend fun getTodoById(id: Int): TodoItem?
    
    /**
     * Insert a new todo item.
     */
    suspend fun insertTodo(todo: TodoItem)
    
    /**
     * Delete a todo item.
     */
    suspend fun deleteTodo(todo: TodoItem)
    
    /**
     * Update an existing todo item.
     */
    suspend fun updateTodo(todo: TodoItem)
    
    /**
     * Toggle the completion status of a todo item.
     */
    suspend fun toggleTodoStatus(todo: TodoItem)
}

