package com.example.rk2.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rk2.domain.model.Priority
import com.example.rk2.domain.model.TodoItem
import com.example.rk2.presentation.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Todo Detail Screen - Screen for viewing and editing todo details.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    todoId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    // Load todo when ID changes
    LaunchedEffect(todoId) {
        if (todoId != null) {
            viewModel.loadTodoById(todoId)
        }
    }
    
    // Collect the todo item from ViewModel
    val todoItem by viewModel.selectedTodo.collectAsStateWithLifecycle()
    val isLoading = todoId != null && todoItem == null
    
    // Form state
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var dueDate by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var titleError by remember { mutableStateOf(false) }
    
    // Update form when todoItem changes
    LaunchedEffect(todoItem) {
        todoItem?.let {
            title = it.title
            description = it.description ?: ""
            priority = it.priority
            dueDate = it.dueDate
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Task") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showDeleteDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                    } else if (todoItem != null) {
                        val updatedTodo = todoItem!!.copy(
                            title = title.trim(),
                            description = description.takeIf { it.isNotBlank() },
                            priority = priority,
                            dueDate = dueDate
                        )
                        viewModel.updateTodo(updatedTodo)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Save Changes")
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (todoItem == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Task not found",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title Field
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = false
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = titleError,
                    supportingText = if (titleError) {
                        { Text("Title is required") }
                    } else null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description Field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    minLines = 3,
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Priority Selector
                Text(
                    text = "Priority",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                PrioritySelector(
                    selectedPriority = priority,
                    onPrioritySelected = { priority = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Date Picker
                DatePickerSection(
                    dueDate = dueDate,
                    onDateSelected = { date ->
                        dueDate = date
                        showDatePicker = false
                    },
                    onDatePickerClick = { showDatePicker = true }
                )

                Spacer(modifier = Modifier.height(80.dp)) // Space for FAB
            }
        }

        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                initialDate = dueDate,
                onDateSelected = { date ->
                    dueDate = date
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }

        // Delete Confirmation Dialog
        if (showDeleteDialog && todoItem != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Task") },
                text = { Text("Are you sure you want to delete this task?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteTodo(todoItem!!)
                            showDeleteDialog = false
                            onNavigateBack()
                        }
                    ) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrioritySelector(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Priority.values().forEach { priority ->
            val isSelected = selectedPriority == priority
            val selectedContainerColor = when (priority) {
                Priority.HIGH -> MaterialTheme.colorScheme.errorContainer
                Priority.MEDIUM -> Color(0xFFFFE0B2) // Light Orange
                Priority.LOW -> MaterialTheme.colorScheme.tertiaryContainer
            }
            val selectedLabelColor = when (priority) {
                Priority.HIGH -> MaterialTheme.colorScheme.onErrorContainer
                Priority.MEDIUM -> Color(0xFFE65100) // Dark Orange
                Priority.LOW -> MaterialTheme.colorScheme.onTertiaryContainer
            }

            FilterChip(
                selected = isSelected,
                onClick = { onPrioritySelected(priority) },
                label = { Text(priority.name) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (isSelected) selectedContainerColor else MaterialTheme.colorScheme.surface,
                    selectedLabelColor = if (isSelected) selectedLabelColor else MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
private fun DatePickerSection(
    dueDate: Long?,
    onDateSelected: (Long?) -> Unit,
    onDatePickerClick: () -> Unit
) {
    Column {
        Text(
            text = "Due Date",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (dueDate != null) {
                    formatDate(dueDate)
                } else {
                    "No due date"
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                color = if (dueDate != null) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                }
            )

            IconButton(onClick = onDatePickerClick) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Select Date"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    initialDate: Long?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Due Date") },
        text = {
            DatePicker(state = datePickerState)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it)
                    } ?: onDateSelected(null)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}

