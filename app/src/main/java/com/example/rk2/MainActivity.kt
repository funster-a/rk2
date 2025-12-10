package com.example.rk2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rk2.domain.model.AppTheme
import com.example.rk2.presentation.navigation.Screen
import com.example.rk2.presentation.ui.screens.AddTodoScreen
import com.example.rk2.presentation.ui.screens.SettingsScreen
import com.example.rk2.presentation.ui.screens.TodoDetailScreen
import com.example.rk2.presentation.ui.screens.TodoListScreen
import com.example.rk2.presentation.ui.theme.ToDoAppTheme
import com.example.rk2.presentation.viewmodel.SettingsViewModel
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
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val theme by settingsViewModel.theme.collectAsStateWithLifecycle()
    val systemDarkTheme = isSystemInDarkTheme()
    
    // Determine dark theme based on AppTheme
    val darkTheme = when (theme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> systemDarkTheme
    }
    
    ToDoAppTheme(darkTheme = darkTheme) {
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
                    TodoListScreen(
                        onNavigateToAdd = {
                            navController.navigate(Screen.AddTodo.route)
                        },
                        onNavigateToDetail = { todoId ->
                            navController.navigate(Screen.TodoDetail.createRoute(todoId))
                        },
                        onNavigateToSettings = {
                            navController.navigate(Screen.Settings.route)
                        }
                    )
                }
                
                composable(Screen.AddTodo.route) {
                    AddTodoScreen(
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                
                composable(
                    route = Screen.TodoDetail.route
                ) { backStackEntry ->
                    val todoId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    TodoDetailScreen(
                        todoId = todoId,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
