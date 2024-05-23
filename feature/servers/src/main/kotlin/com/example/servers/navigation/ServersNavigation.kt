package com.example.servers.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.servers.CreateChannelRoute
import com.example.servers.CreateServerRoute
import com.example.servers.ServersRoute

const val SERVERS_ROUTE = "servers"
const val CREATE_SERVER_ROUTE = "create_server"
const val CREATE_CHANNEL_ROUTE = "create_channel/{serverId}"

fun NavController.navigateToServers(navOptions: NavOptions? = null) {
    navigate(SERVERS_ROUTE, navOptions)
}

fun NavGraphBuilder.serversScreen(
    onChannelClick: (String) -> Unit,
    onCreateServerClick: () -> Unit,
    onCreateChannelClick: (String) -> Unit
) {
    composable(SERVERS_ROUTE) {
        ServersRoute(onChannelClick, onCreateServerClick, onCreateChannelClick)
    }
}

fun NavController.navigateToCreateServer(navOptions: NavOptions? = null) {
    navigate(CREATE_SERVER_ROUTE, navOptions)
}

fun NavGraphBuilder.createServerScreen(
    onBackPressed: () -> Unit,
    onCreateServerClick: () -> Unit,
    onJoinServerClick: () -> Unit
) {
    composable(CREATE_SERVER_ROUTE) {
        CreateServerRoute(onBackPressed, onCreateServerClick, onJoinServerClick)
    }
}

fun NavController.navigateToCreateChannel(serverId: String, navOptions: NavOptions? = null) {
    val route = CREATE_CHANNEL_ROUTE.replace("{serverId}", serverId)
    navigate(route, navOptions)
}

fun NavGraphBuilder.createChannelScreen(
    onBackPressed: () -> Unit,
    onCreateChannelClick: () -> Unit
) {
    composable(CREATE_CHANNEL_ROUTE) {backStackEntry ->
        val serverId = backStackEntry.arguments?.getString("serverId")
        if (serverId != null) {
            CreateChannelRoute(serverId, onBackPressed, onCreateChannelClick)
        }
    }
}