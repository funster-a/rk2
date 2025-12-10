package com.example.rk2.data.repository

import com.example.rk2.data.local.dao.TodoDao
import com.example.rk2.data.mapper.TodoMapper.toDomain
import com.example.rk2.data.mapper.TodoMapper.toEntity
import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of TodoRepository.
 * This bridges the Domain and Data layers, converting between Domain models and Data entities.
 */
class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    
    override fun getAllTodos(): Flow<List<TodoItem>> {
        return todoDao.getAllTodos().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getTodoById(id: Int): TodoItem? {
        return todoDao.getTodoById(id)?.toDomain()
    }
    
    override suspend fun insertTodo(todo: TodoItem) {
        todoDao.insertTodo(todo.toEntity())
    }
    
    override suspend fun deleteTodo(todo: TodoItem) {
        todoDao.deleteTodo(todo.toEntity())
    }
    
    override suspend fun updateTodo(todo: TodoItem) {
        todoDao.updateTodo(todo.toEntity())
    }
    
    override suspend fun toggleTodoStatus(todo: TodoItem) {
        todoDao.updateTodoStatus(todo.id, !todo.isCompleted)
    }
}

