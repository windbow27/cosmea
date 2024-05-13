package com.example.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.register.RegisterRoute

const val REGISTER_ROUTE = "register"

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    navigate(REGISTER_ROUTE, navOptions)
}

fun NavGraphBuilder.registerScreen(
    onTopicClick: (String) -> Unit,
) {
    composable(REGISTER_ROUTE) {
        RegisterRoute(onTopicClick)
    }
}