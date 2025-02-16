package com.velichkomarija.myapplication

import androidx.navigation.NavHostController
import com.velichkomarija.myapplication.AppScreens.MAIN_SCREEN
import com.velichkomarija.myapplication.AppScreens.TODO_MAIN_SCREEN

private object AppScreens {
    const val MAIN_SCREEN = "main"
    const val TODO_MAIN_SCREEN = "todo"
}

object AppDestination {
    const val MAIN_ROUTE = MAIN_SCREEN
    const val TODO_ROUTE = TODO_MAIN_SCREEN
}

class NavigateActions(private val navController: NavHostController) {
    fun navigateToTodoList() {
        navController.navigate(TODO_MAIN_SCREEN)
    }
}