package com.example.cosmea.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cosmea.R
import com.example.designsystem.icon.Icons

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    SERVERS(
        selectedIcon = Icons.Upcoming,
        unselectedIcon = Icons.UpcomingBorder,
        iconTextId = R.string.icon_text_servers,
        titleTextId = R.string.title_text_servers
    ),
    MESSAGES(
        selectedIcon = Icons.Bookmarks,
        unselectedIcon = Icons.BookmarksBorder,
        iconTextId = R.string.icon_text_messages,
        titleTextId = R.string.title_text_messages
    ),
    NOTIFICATIONS(
        selectedIcon = Icons.Settings,
        unselectedIcon = Icons.Settings,
        iconTextId = R.string.icon_text_notifications,
        titleTextId = R.string.title_text_notifications
    ),
    PROFILE(
        selectedIcon = Icons.Person,
        unselectedIcon = Icons.Person,
        iconTextId = R.string.icon_text_profile,
        titleTextId = R.string.title_text_profile
    )
}