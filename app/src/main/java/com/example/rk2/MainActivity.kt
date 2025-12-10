package com.example.rk2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rk2.presentation.navigation.Screen
import com.example.rk2.presentation.ui.screens.AddTodoScreen
import com.example.rk2.presentation.ui.screens.TodoDetailScreen
import com.example.rk2.presentation.ui.screens.TodoListScreen
import com.example.rk2.presentation.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for the Todo application.
 * Sets up Hilt, Compose, and Navigation.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(
                        navController = navController,
                        startDestination = Screen.TodoList.route
                    ) {
                        composable(Screen.TodoList.route) {
                            TodoListScreen()
                        }
                        
                        composable(Screen.AddTodo.route) {
                            AddTodoScreen()
                        }
                        
                        composable(
                            route = Screen.TodoDetail.route
                        ) { backStackEntry ->
                            val todoId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                            TodoDetailScreen(todoId = todoId)
                        }
                    }
                }
            }
        }
    }
}
