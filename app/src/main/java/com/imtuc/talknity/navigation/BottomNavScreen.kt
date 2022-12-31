package com.imtuc.talknity.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object Home : BottomNavScreen(
        "home",
        "Home",
        Icons.Outlined.Home,
        "home_screen"
    )

    object Discussions : BottomNavScreen(
        "discussion",
        "Discussion",
        Icons.Outlined.Newspaper,
        "discussions_screen"
    )

    object Community : BottomNavScreen(
        "community",
        "Community",
        Icons.Outlined.People,
        "community_screen"
    )

    object Profile : BottomNavScreen(
        "settings",
        "Settings",
        Icons.Outlined.Person,
        "profile_screen"
    )

    object Items {
        val list = listOf(
            Home, Discussions, Community, Profile
        )
    }
}
