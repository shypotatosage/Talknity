package com.imtuc.talknity.navigation

sealed class Screen(val route: String) {
    object Home:Screen(route = "home_screen")
    object Splash:Screen(route = "splash_screen")
    object Login:Screen(route = "login_screen")
    object Register:Screen(route = "register_screen")
    object Community:Screen(route = "community_screen")
    object CommunityCategory:Screen(route = "community_category_screen")
    object SelectedCommunityCategory:Screen(route = "selected_community_category_screen")
    object CommunityDetail:Screen(route = "community_detail_screen")
    object CreateCommunity:Screen(route = "create_community_screen")
}
