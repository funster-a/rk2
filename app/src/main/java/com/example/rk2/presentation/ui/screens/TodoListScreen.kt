package com.example.rk2.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Todo List Screen - Main screen displaying all todos.
 * This is a skeleton implementation that will be expanded later.
 */
@Composable
fun TodoListScreen() {
    Scaffold(
        topBar = {
            // TopBar will be implemented later
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Todo List Screen",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

