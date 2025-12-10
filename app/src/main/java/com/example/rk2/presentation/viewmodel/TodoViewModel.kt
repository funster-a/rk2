package com.example.rk2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rk2.domain.model.Priority
import com.example.rk2.domain.model.TodoItem
import com.example.rk2.domain.usecase.AddTodoUseCase
import com.example.rk2.domain.usecase.DeleteTodoUseCase
import com.example.rk2.domain.usecase.GetTodoByIdUseCase
import com.example.rk2.domain.usecase.GetTodosUseCase
import com.example.rk2.domain.usecase.ToggleTodoStatusUseCase
import com.example.rk2.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sort order options for the todo list.
 */
enum class SortOrder {
    DATE_ASC,        // Earliest due date first
    PRIORITY_DESC,   // High priority first
    TITLE_ASC        // A-Z
}

/**
 * Data class representing analytics statistics for todos.
 */
data class TodoAnalytics(
    val totalCount: Int,
    val completedCount: Int,
    val progress: Float // 0.0 to 1.0
)

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
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _sortOrder = MutableStateFlow(SortOrder.DATE_ASC)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()
    
    private val _selectedTodo = MutableStateFlow<TodoItem?>(null)
    val selectedTodo: StateFlow<TodoItem?> = _selectedTodo.asStateFlow()
    
    /**
     * Combined flow that filters and sorts todos based on search query and sort order.
     */
    val todos = combine(
        getTodosUseCase(),
        _searchQuery,
        _sortOrder
    ) { list, query, sort ->
        // 1. Filter by query (title or description contains query, ignore case)
        val filtered = if (query.isBlank()) {
            list
        } else {
            list.filter {
                it.title.contains(query, ignoreCase = true) ||
                (it.description?.contains(query, ignoreCase = true) == true)
            }
        }
        
        // 2. Sort
        when (sort) {
            SortOrder.DATE_ASC -> filtered.sortedBy { it.dueDate ?: Long.MAX_VALUE }
            SortOrder.PRIORITY_DESC -> filtered.sortedByDescending { 
                // Sort by priority ordinal: HIGH=2, MEDIUM=1, LOW=0
                it.priority.ordinal 
            }
            SortOrder.TITLE_ASC -> filtered.sortedBy { it.title.lowercase() }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    /**
     * Analytics derived from the current filtered todos list.
     */
    val analytics = todos.map { todoList ->
        val totalCount = todoList.size
        val completedCount = todoList.count { it.isCompleted }
        val progress = if (totalCount > 0) {
            completedCount.toFloat() / totalCount.toFloat()
        } else {
            0f
        }
        TodoAnalytics(
            totalCount = totalCount,
            completedCount = completedCount,
            progress = progress
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TodoAnalytics(0, 0, 0f)
    )
    
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
    
    /**
     * Update the search query.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    
    /**
     * Update the sort order.
     */
    fun onSortOrderChange(order: SortOrder) {
        _sortOrder.value = order
    }
}

