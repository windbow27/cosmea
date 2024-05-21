
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.conversation.navigation.conversationScreen
import com.example.conversation.navigation.navigateToConversation
import com.example.cosmea.ui.AppState
import com.example.login.navigation.LOGIN_ROUTE
import com.example.login.navigation.loginScreen
import com.example.login.navigation.navigateToLogin
import com.example.messages.navigation.addFriendScreen
import com.example.messages.navigation.messagesScreen
import com.example.messages.navigation.navigateToAddFriend
import com.example.notifications.navigation.notificationsScreen
import com.example.profile.navigation.AccountManagementScreen
import com.example.profile.navigation.ChangePasswordScreen
import com.example.profile.navigation.navigateToPasswordChange
import com.example.profile.navigation.navigateToProfile
import com.example.profile.navigation.navigateToProfileAccount
import com.example.profile.navigation.navigateToProfileView
import com.example.profile.navigation.profileScreen
import com.example.profile.navigation.profileViewScreen
import com.example.register.navigation.navigateToRegister
import com.example.register.navigation.registerScreen
import com.example.servers.navigation.SERVERS_ROUTE
import com.example.servers.navigation.createChannelScreen
import com.example.servers.navigation.createServerScreen
import com.example.servers.navigation.navigateToCreateChannel
import com.example.servers.navigation.navigateToCreateServer
import com.example.servers.navigation.navigateToServers
import com.example.servers.navigation.serversScreen

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = if (appState.isTimeoutSession) LOGIN_ROUTE else SERVERS_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        serversScreen(onChannelClick = navController::navigateToConversation, onCreateServerClick = navController::navigateToCreateServer, onCreateChannelClick = navController::navigateToCreateChannel)
        createServerScreen(onBackPressed = navController::popBackStack, onCreateServerClick = navController::navigateToServers, onJoinServerClick = navController::navigateToServers)
        createChannelScreen(onBackPressed = navController::popBackStack, onCreateChannelClick = navController::navigateToServers)
        conversationScreen(onBackPressed = navController::popBackStack)
        messagesScreen(onChannelClick = navController::navigateToConversation, onAddFriendScreenClick = navController::navigateToAddFriend)
        addFriendScreen(onBackPressed = navController::popBackStack)
        notificationsScreen()
        AccountManagementScreen (onPasswordChangeClick = navController::navigateToPasswordChange, onBackClick = navController::navigateToProfile, onLogoutClick = navController::navigateToLogin)
        ChangePasswordScreen (onBackClick = navController::navigateToProfileAccount)
        profileScreen (onLogoutClick = navController::navigateToLogin, onClickProfile = navController::navigateToProfileView, onClickAccount = navController::navigateToProfileAccount)
        profileViewScreen (onBackClick = navController::navigateToProfile)
        registerScreen (onRegisterClick = navController::navigateToLogin, redirectToLogin = navController::navigateToProfile)
        loginScreen (onLoginClick = navController::navigateToServers , redirectToRegister = navController::navigateToRegister)
    }
}

