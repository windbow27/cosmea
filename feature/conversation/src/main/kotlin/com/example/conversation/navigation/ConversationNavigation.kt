package com.example.conversation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.conversation.ConversationRoute

const val CONVERSATION_ROUTE = "conversation/{id}"

fun NavController.navigateToConversation(id: String, navOptions: NavOptions? = null) {
    val route = "conversation/$id"
    navigate(route, navOptions)
}

fun NavGraphBuilder.conversationScreen(
    onBackPressed: () -> Unit,
) {
    composable(CONVERSATION_ROUTE) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")
        ConversationRoute(id, onBackPressed)
    }
}