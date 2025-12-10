package com.example.rk2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rk2.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Todo operations.
 * Provides methods to interact with the todos table in the database.
 */
@Dao
interface TodoDao {
    /**
     * Get all todos as a Flow for reactive updates.
     */
    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>
    
    /**
     * Get a single todo by ID.
     */
    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntity?
    
    /**
     * Insert a new todo item.
     * On conflict, replace the existing item.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)
    
    /**
     * Delete a todo item.
     */
    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
    
    /**
     * Update an existing todo item.
     */
    @Update
    suspend fun updateTodo(todo: TodoEntity)
    
    /**
     * Toggle the completion status of a todo item.
     */
    @Query("UPDATE todos SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateTodoStatus(id: Int, isCompleted: Boolean)
}

