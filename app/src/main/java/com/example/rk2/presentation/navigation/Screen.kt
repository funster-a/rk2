package com.example.rk2.presentation.navigation

/**
 * Sealed class representing all navigation routes in the app.
 * This provides type-safe navigation.
 */
sealed class Screen(val route: String) {
    object TodoList : Screen("todo_list")
    object AddTodo : Screen("add_todo")
    object TodoDetail : Screen("todo_detail/{id}") {
        fun createRoute(id: Int) = "todo_detail/$id"
    }
}

