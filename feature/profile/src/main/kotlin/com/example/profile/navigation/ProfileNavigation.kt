package com.example.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.profile.ProfileRoute

const val PROFILE_ROUTE = "profile"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(PROFILE_ROUTE, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onLogoutClick: () -> Unit,
    onClickProfile: () -> Unit
) {
    composable(PROFILE_ROUTE) {
        ProfileRoute(onLogoutClick, onClickProfile)
    }
}