package com.example.messages.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.messages.MessagesRoute

const val MESSAGES_ROUTE = "messages"

fun NavController.navigateToMessages(navOptions: NavOptions? = null) {
    navigate(MESSAGES_ROUTE, navOptions)
}

fun NavGraphBuilder.messagesScreen(
    onChannelClick: (String) -> Unit
) {
    composable(MESSAGES_ROUTE) {
        MessagesRoute(onChannelClick)
    }
}