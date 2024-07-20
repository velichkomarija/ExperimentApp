package com.velichkomarija.myapplication

import com.velichkomarija.myapplication.AppScreens.PROFILE_SCREEN
import com.velichkomarija.myapplication.AppScreens.TODO_MAIN_SCREEN

private object AppScreens {
    const val PROFILE_SCREEN = "profile"
    const val TODO_MAIN_SCREEN = "todo"
}

object AppDestination {
    const val PROFILE_ROUTE = PROFILE_SCREEN
    const val TODO_ROUTE = TODO_MAIN_SCREEN
}