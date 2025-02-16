package com.velichkomarija.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.velichkomarija.myapplication.main.MainScreen
import com.velichkomarija.myapplication.todo.TodoScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.MAIN_ROUTE,
    navActions: NavigateActions = remember(navController) {
        NavigateActions(navController)
    }
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(AppDestination.MAIN_ROUTE) {
            MainScreen(
                openTodoList = { navActions.navigateToTodoList() }
            )
        }

        composable(AppDestination.TODO_ROUTE) {
            TodoScreen(
                onTaskClick = {},
                onAddNewTask = {}
            )
        }
    }
}