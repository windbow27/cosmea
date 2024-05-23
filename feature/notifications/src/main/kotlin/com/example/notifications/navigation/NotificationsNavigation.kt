package com.example.notifications.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.notifications.NotificationsRoute

const val NOTIFICATIONS_ROUTE = "notifications"

fun NavController.navigateToNotifications(navOptions: NavOptions? = null) {
    navigate(NOTIFICATIONS_ROUTE, navOptions)
}

fun NavGraphBuilder.notificationsScreen(
) {
    composable(NOTIFICATIONS_ROUTE) {
        NotificationsRoute()
    }
}