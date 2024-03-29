package com.imtuc.talknity.navigation

sealed class Screen(val route: String) {
    object Home:Screen(route = "home_screen")
    object Splash:Screen(route = "splash_screen")
    object Login:Screen(route = "login_screen")
    object Register:Screen(route = "register_screen")
    object Community:Screen(route = "community_screen")
    object CommunityCategory:Screen(route = "community_category_screen")
    object SelectedCommunityCategory:Screen(route = "selected_community_category_screen/{category_id}/{category_name}") {
        fun passParam(
            id: String,
            name: String
        ): String {
            return "selected_community_category_screen/$id/$name"
        }
    }
    object CommunityDetail:Screen(route = "community_detail_screen")
    object CreateCommunity:Screen(route = "create_community_screen")
    object OwnedCommunity:Screen(route = "owned_community_screen")
    object Discussions:Screen(route = "discussions_screen")
    object OwnedDiscussion:Screen(route = "owned_discussions_screen")
    object CreateDiscussion:Screen(route = "create_discussions_screen")
}
