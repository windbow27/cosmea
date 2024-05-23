package com.example.messages.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.messages.AddFriendRoute
import com.example.messages.MessagesRoute

const val MESSAGES_ROUTE = "messages"
const val ADD_FRIEND_ROUTE = "add_friend"

fun NavController.navigateToMessages(navOptions: NavOptions? = null) {
    navigate(MESSAGES_ROUTE, navOptions)
}

fun NavGraphBuilder.messagesScreen(
    onChannelClick: (String) -> Unit,
    onAddFriendScreenClick: () -> Unit
) {
    composable(MESSAGES_ROUTE) {
        MessagesRoute(onChannelClick, onAddFriendScreenClick)
    }
}

fun NavController.navigateToAddFriend(navOptions: NavOptions? = null) {
    navigate(ADD_FRIEND_ROUTE, navOptions)
}

fun NavGraphBuilder.addFriendScreen(
    onBackPressed: () -> Unit
) {
    composable(ADD_FRIEND_ROUTE) {
        AddFriendRoute(onBackPressed)
    }
}