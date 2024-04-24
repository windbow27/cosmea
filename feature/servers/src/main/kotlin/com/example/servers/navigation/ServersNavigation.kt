package com.example.servers.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.servers.ServersRoute

const val SERVERS_ROUTE = "servers"

fun NavController.navigateToServers(navOptions: NavOptions? = null) {
    navigate(SERVERS_ROUTE, navOptions)
}

fun NavGraphBuilder.serversScreen(
    onTopicClick: (String) -> Unit
) {
    composable(SERVERS_ROUTE) {
        ServersRoute(onTopicClick)
    }
}