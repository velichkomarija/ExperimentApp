package com.velichkomarija.everydaykit

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.velichkomarija.everydaykit.AppScreens.ADD_UPDATE_SCREEN
import com.velichkomarija.everydaykit.AppScreens.MAIN_SCREEN
import com.velichkomarija.everydaykit.AppScreens.TODO_MAIN_SCREEN
import com.velichkomarija.everydaykit.AppScreens.USER_MESSAGE_ARG
import com.velichkomarija.everydaykit.TodoDestinationArgs.TASK_ID_ARG
import com.velichkomarija.everydaykit.TodoDestinationArgs.TITLE_ARG

private object AppScreens {
    const val MAIN_SCREEN = "main"
    const val TODO_MAIN_SCREEN = "todo"
    const val ADD_UPDATE_SCREEN = "addOrUpdate"
    const val USER_MESSAGE_ARG = "userMessage"
}

object TodoDestinationArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}

object AppDestination {
    const val MAIN_ROUTE = MAIN_SCREEN
    const val TODO_ROUTE = "$TODO_MAIN_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val ADD_OR_UPDATE_ROUTE = "$ADD_UPDATE_SCREEN/{$TITLE_ARG}?$TASK_ID_ARG={$TASK_ID_ARG}"
}

class NavigateActions(private val navController: NavHostController) {
    fun navigateToTodoList(userMessage: Int = 0) {
        navController.navigate(
            TODO_MAIN_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }

    }

    fun navigateToAddOrUpdateTask(title: Int, taskId: String?) {
        navController.navigate(
            "$ADD_UPDATE_SCREEN/$title".let {
                if (taskId != null) "$it?$TASK_ID_ARG=$taskId" else it
            }
        )
    }
}