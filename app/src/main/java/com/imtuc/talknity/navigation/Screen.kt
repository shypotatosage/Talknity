package com.imtuc.talknity.navigation

import android.util.Log

sealed class Screen(val route: String) {
    object Splash:Screen(route = "splash_screen")
    object Login:Screen(route = "login_screen")
    object Register:Screen(route = "register_screen")
    object CommunityCategory:Screen(route = "community_category_screen")
    object SelectedCommunityCategory:Screen(route = "selected_community_category_screen/{category_id}/{category_name}") {
        fun passParam(
            id: String,
            name: String
        ): String {
            return "selected_community_category_screen/$id/$name"
        }
    }
    object CommunityDetail:Screen(route = "community_detail_screen/{community_id}") {
        fun passParam(
            community_id: String
        ): String {
            return "community_detail_screen/$community_id"
        }
    }
    object CreateCommunity:Screen(route = "create_community_screen")
    object EditCommunity:Screen(route = "edit_community_screen/{community_id}") {
        fun passParam(
            community_id: String
        ): String {
            return "edit_community_screen/$community_id"
        }
    }
    object OwnedCommunity:Screen(route = "owned_community_screen")
    object IndividualDiscussion:Screen(route = "individual_discussions_screen/{post_id}") {
        fun passParam(
            post_id: String
        ): String {
            return "individual_discussions_screen/$post_id"
        }
    }
    object OwnedDiscussion:Screen(route = "owned_discussions_screen")
    object OwnedComments:Screen(route = "owned_comments_screen")
    object CreateDiscussion:Screen(route = "create_discussions_screen")
    object EditDiscussion:Screen(route = "edit_discussions_screen/{post_id}") {
        fun passParam(
            post_id: String
        ): String {
            return "edit_discussions_screen/$post_id"
        }
    }
    object EditProfile:Screen(route = "edit_profile_screen/{user_displayname}/{user_username}/{user_email}/{user_image}") {
        fun passParam(
            user_displayname: String,
            user_username: String,
            user_email: String,
            user_image: String
        ): String {
            var img = "empty"

            if (user_image != "") {
                img = user_image.substring(12, user_image.length)
            }

            return "edit_profile_screen/$user_displayname/$user_username/$user_email/$img"
        }
    }
}
