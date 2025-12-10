package com.example.rk2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.usecase.AddTodoUseCase
import com.example.rk2.domain.usecase.DeleteTodoUseCase
import com.example.rk2.domain.usecase.GetTodoByIdUseCase
import com.example.rk2.domain.usecase.GetTodosUseCase
import com.example.rk2.domain.usecase.ToggleTodoStatusUseCase
import com.example.rk2.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing Todo state and business logic.
 * Uses Hilt for dependency injection and exposes StateFlow for UI updates.
 */
@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleTodoStatusUseCase: ToggleTodoStatusUseCase
) : ViewModel() {
    
    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()
    
    private val _selectedTodo = MutableStateFlow<TodoItem?>(null)
    val selectedTodo: StateFlow<TodoItem?> = _selectedTodo.asStateFlow()
    
    init {
        loadTodos()
    }
    
    /**
     * Load all todos from the repository.
     */
    private fun loadTodos() {
        viewModelScope.launch {
            getTodosUseCase().collect { todoList ->
                _todos.value = todoList
            }
        }
    }
    
    /**
     * Add a new todo item.
     */
    fun addTodo(todo: TodoItem) {
        viewModelScope.launch {
            addTodoUseCase(todo)
        }
    }
    
    /**
     * Delete a todo item.
     */
    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            deleteTodoUseCase(todo)
        }
    }
    
    /**
     * Toggle the completion status of a todo item.
     */
    fun toggleTodoStatus(todo: TodoItem) {
        viewModelScope.launch {
            toggleTodoStatusUseCase(todo)
        }
    }
    
    /**
     * Load a todo item by ID and update the selectedTodo StateFlow.
     */
    fun loadTodoById(id: Int) {
        viewModelScope.launch {
            _selectedTodo.value = getTodoByIdUseCase(id)
        }
    }
    
    /**
     * Update an existing todo item.
     */
    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            updateTodoUseCase(todo)
        }
    }
}

