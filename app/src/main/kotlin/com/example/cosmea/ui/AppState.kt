package com.example.cosmea.ui


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.cosmea.navigation.TopLevelDestination
import com.example.messages.navigation.MESSAGES_ROUTE
import com.example.messages.navigation.navigateToMessages
import com.example.model.EXPIRED_TIME
import com.example.notifications.navigation.NOTIFICATIONS_ROUTE
import com.example.notifications.navigation.navigateToNotifications
import com.example.profile.navigation.PROFILE_ROUTE
import com.example.profile.navigation.navigateToProfile
import com.example.servers.navigation.SERVERS_ROUTE
import com.example.servers.navigation.navigateToServers
import java.time.Instant


@Preview
@Composable
fun rememberAppState(
    context: Context,
    navController: NavHostController = rememberNavController()
): AppState {
    return AppState(context, navController)
}

@Stable
class AppState(
    private val context: Context,
    val navController: NavHostController,
) {
    val sharedPref = context.getSharedPreferences("CosmeaApp", Context.MODE_PRIVATE)
    val currentUserId = sharedPref.getString("currentUserId", null)
    val lastLoginTimestamp = sharedPref.getString("session", "0")
    val currentTimestamp = Instant.now().toEpochMilli()
    val isTimeoutSession = (currentTimestamp > (lastLoginTimestamp!!.toLong() + EXPIRED_TIME) || currentUserId == null)

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            SERVERS_ROUTE -> TopLevelDestination.SERVERS
            MESSAGES_ROUTE -> TopLevelDestination.MESSAGES
            NOTIFICATIONS_ROUTE -> TopLevelDestination.NOTIFICATIONS
            PROFILE_ROUTE -> TopLevelDestination.PROFILE
            else -> null
        }


    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.SERVERS -> navController.navigateToServers(topLevelNavOptions)
                TopLevelDestination.MESSAGES -> navController.navigateToMessages(topLevelNavOptions)
                TopLevelDestination.NOTIFICATIONS -> navController.navigateToNotifications(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
            }
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
