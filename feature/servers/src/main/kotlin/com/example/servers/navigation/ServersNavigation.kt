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
const val CREATE_CHANNEL_ROUTE = "create_channel"

fun NavController.navigateToServers(navOptions: NavOptions? = null) {
    navigate(SERVERS_ROUTE, navOptions)
}

fun NavGraphBuilder.serversScreen(
    onChannelClick: (String) -> Unit,
    onCreateServerClick: () -> Unit,
    onCreateChannelCLick: () -> Unit
) {
    composable(SERVERS_ROUTE) {
        ServersRoute(onChannelClick, onCreateServerClick, onCreateChannelCLick)
    }
}

fun NavController.navigateToCreateServer(navOptions: NavOptions? = null) {
    navigate(CREATE_SERVER_ROUTE, navOptions)
}

fun NavGraphBuilder.createServerScreen(
    onBackPressed: () -> Unit,
    onCreateServerClick: () -> Unit
) {
    composable(CREATE_SERVER_ROUTE) {
        CreateServerRoute(onBackPressed, onCreateServerClick)
    }
}

fun NavController.navigateToCreateChannel(navOptions: NavOptions? = null) {
    navigate(CREATE_CHANNEL_ROUTE, navOptions)
}

fun NavGraphBuilder.createChannelScreen(
    onBackPressed: () -> Unit,
    onCreateChannelClick: () -> Unit
) {
    composable(CREATE_CHANNEL_ROUTE) {
        CreateChannelRoute(onBackPressed, onCreateChannelClick)
    }
}