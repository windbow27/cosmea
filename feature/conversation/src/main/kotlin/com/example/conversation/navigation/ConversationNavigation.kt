package com.example.conversation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.conversation.ConversationRoute

// In ConversationNavigation.kt
const val CONVERSATION_ROUTE = "conversation/{id}"

fun NavController.navigateToConversation(id: String, navOptions: NavOptions? = null) {
    navigate(CONVERSATION_ROUTE.replace("{id}", id), navOptions)
}

fun NavGraphBuilder.conversationScreen(
    onNavIconPressed: () -> Unit
) {
    composable(CONVERSATION_ROUTE) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")
        ConversationRoute(id, onNavIconPressed)
    }
}