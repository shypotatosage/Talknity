package com.imtuc.talknity.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.imtuc.talknity.view.*
import com.imtuc.talknity.viewmodel.AuthViewModel
import com.imtuc.talknity.viewmodel.CommunityViewModel
import com.imtuc.talknity.viewmodel.HomeViewModel
import com.imtuc.talknity.viewmodel.PostViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    communityViewModel: CommunityViewModel,
    postViewModel: PostViewModel,
    lifecycleOwner: LifecycleOwner
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route
        ) {
            AnimatedSplashScreen(navController = navController)
        }

        composable(
            route = BottomNavScreen.Home.route
        ) {
            HomeScreen(homeViewModel = homeViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.Login.route
        ) {
            Login(authViewModel = authViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.Register.route
        ) {
            Register(authViewModel = authViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = BottomNavScreen.Community.route
        ) {
            EnterCommunity(navController = navController)
        }

        composable(
            route = Screen.CommunityCategory.route
        ) {
            CommunityCategories(communityViewModel = communityViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.SelectedCommunityCategory.route,
            arguments = listOf(
                navArgument("category_id") {
                    type = NavType.StringType
                },
                navArgument("category_name") {
                    type = NavType.StringType
                }
            )
        ) {
            SelectedCategoryCommunityList(category_id = it.arguments?.getString("category_id").toString(), category_name = it.arguments?.getString("category_name").toString(), communityViewModel = communityViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.CommunityDetail.route,
            arguments = listOf(
                navArgument("community_id") {
                    type = NavType.StringType
                }
            )
        ) {
            CommunityDetail(community_id = it.arguments?.getString("community_id").toString(), communityViewModel = communityViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.CreateCommunity.route
        ) {
            CreateCommunity(communityViewModel = communityViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.EditCommunity.route,
            arguments = listOf(
                navArgument("community_id") {
                    type = NavType.StringType
                }
            )
        ) {
            EditCommunity(
                community_id = it.arguments?.getString("community_id").toString(),
                communityViewModel = communityViewModel,
                lifecycleOwner = lifecycleOwner,
                navController = navController
            )
        }

        composable(
            route = Screen.EditDiscussion.route,
            arguments = listOf(
                navArgument("post_id") {
                    type = NavType.StringType
                }
            )
        ) {
            EditDiscussion(post_id = it.arguments?.getString("post_id").toString(), postViewModel = postViewModel, navController = navController, lifecycleOwner = lifecycleOwner)
        }

        composable(
            route = Screen.OwnedCommunity.route
        ) {
            OwnedCommunity(communityViewModel = communityViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = BottomNavScreen.Discussions.route
        ) {
            Discussions(postViewModel = postViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.IndividualDiscussion.route,
            arguments = listOf(
                navArgument("post_id") {
                    type = NavType.StringType
                }
            )
        ) {
            IndividualDiscussion(post_id = it.arguments?.getString("post_id").toString(), postViewModel = postViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.OwnedDiscussion.route
        ) {
            OwnedDiscussions(postViewModel = postViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.OwnedComments.route
        ) {
            OwnedComments(postViewModel = postViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.CreateDiscussion.route
        ) {
            CreateDiscussion(postViewModel = postViewModel, navController = navController, lifecycleOwner = lifecycleOwner)
        }

        composable(
            route = BottomNavScreen.Profile.route
        ) {
            Profile(authViewModel = authViewModel, navController = navController, lifecycleOwner = lifecycleOwner)
        }

        composable(
            route = Screen.EditProfile.route,
            arguments = listOf(
                navArgument("user_displayname") {
                    type = NavType.StringType
                },
                navArgument("user_username") {
                    type = NavType.StringType
                },
                navArgument("user_email") {
                    type = NavType.StringType
                },
                navArgument("user_image") {
                    type = NavType.StringType
                }
            )
        ) {
            EditProfile(
                authViewModel = authViewModel,
                user_displayname = it.arguments?.getString("user_displayname").toString(),
                user_username = it.arguments?.getString("user_username").toString(),
                user_email = it.arguments?.getString("user_email").toString(),
                user_image = it.arguments?.getString("user_image").toString(),
                navController = navController,
                lifecycleOwner = lifecycleOwner
            )
        }
    }
}