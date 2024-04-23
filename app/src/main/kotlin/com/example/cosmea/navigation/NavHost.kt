import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cosmea.navigation.TopLevelDestination
import com.example.profile.ProfileScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = TopLevelDestination.PROFILE.name.lowercase()) {
//        composable(TopLevelDestination.SERVERS.name.lowercase()) { ServersScreen(navController) }
//        composable(TopLevelDestination.MESSAGES.name.lowercase()) { MessagesScreen(navController) }
//        composable(TopLevelDestination.NOTIFICATIONS.name.lowercase()) { NotificationsScreen(navController) }
        composable(TopLevelDestination.PROFILE.name.lowercase()) { ProfileScreen() }
    }
}