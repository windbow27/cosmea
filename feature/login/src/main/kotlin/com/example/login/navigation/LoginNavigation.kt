package com.example.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.login.LoginRoute

const val LOGIN_ROUTE = "login"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LOGIN_ROUTE, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onLoginClick: () -> Unit,
    redictToRegister: () -> Unit
) {
    composable(LOGIN_ROUTE) {
        LoginRoute(onLoginClick, redictToRegister)
    }
}