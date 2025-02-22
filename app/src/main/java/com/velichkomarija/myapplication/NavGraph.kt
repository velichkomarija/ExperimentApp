package com.velichkomarija.myapplication

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.velichkomarija.myapplication.TodoDestinationArgs.TASK_ID_ARG
import com.velichkomarija.myapplication.TodoDestinationArgs.TITLE_ARG
import com.velichkomarija.myapplication.TodoDestinationArgs.USER_MESSAGE_ARG
import com.velichkomarija.myapplication.main.MainScreen
import com.velichkomarija.myapplication.todo.TodoScreen
import com.velichkomarija.myapplication.todo.addnew.AddOrUpdateScreen
import com.velichkomarija.myapplication.uicomponents.AddOrUpdateTopAppBar

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

        composable(AppDestination.TODO_ROUTE,
            arguments = listOf(
                navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
            )) { entry ->
            TodoScreen(
                userMessage = entry.arguments?.getInt(USER_MESSAGE_ARG)!!,
                onTaskClick = { task ->
                    navActions.navigateToAddOrUpdateTask(
                        R.string.edit_task,
                        taskId = task.id
                    )
                },
                onAddNewTask = { navActions.navigateToAddOrUpdateTask(R.string.add_task, null) },
                onUserMessageDisplayed = { entry.arguments?.putInt(USER_MESSAGE_ARG, 0) },
            )
        }

        composable(
            AppDestination.ADD_OR_UPDATE_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType },
                navArgument(TASK_ID_ARG) { type = NavType.StringType; nullable = true }
            )
        ) { entry ->
            val taskId = entry.arguments?.getString(TASK_ID_ARG)
            AddOrUpdateScreen(
                topBarTitle = entry.arguments?.getInt(TITLE_ARG),
                onTaskUpdate = {
                    navActions.navigateToTodoList(
                        if (taskId == null) ADD_EDIT_RESULT_OK else EDIT_RESULT_OK
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3
