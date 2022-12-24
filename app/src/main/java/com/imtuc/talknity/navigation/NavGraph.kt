package com.imtuc.talknity.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = Screen.Discussions.route
    ) {
        composable(
            route = Screen.Splash.route
        ) {
            AnimatedSplashScreen(navController = navController)
        }

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(homeViewModel = homeViewModel, lifecycleOwner = lifecycleOwner)
        }

        composable(
            route = Screen.Login.route
        ) {
            Login(authViewModel = authViewModel, lifecycleOwner = lifecycleOwner)
        }

        composable(
            route = Screen.Register.route
        ) {
            Register(authViewModel = authViewModel)
        }

        composable(
            route = Screen.Community.route
        ) {
            EnterCommunity(navController = navController)
        }

        composable(
            route = Screen.CommunityCategory.route
        ) {
            CommunityCategories(communityViewModel = communityViewModel, lifecycleOwner = lifecycleOwner)
        }

        composable(
            route = Screen.CreateCommunity.route
        ) {
            CreateCommunity(communityViewModel = communityViewModel)
        }

        composable(
            route = Screen.OwnedCommunity.route
        ) {
            OwnedCommunity(communityViewModel = communityViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.Discussions.route
        ) {
            Discussions(postViewModel = postViewModel, lifecycleOwner = lifecycleOwner, navController = navController)
        }

        composable(
            route = Screen.CreateDiscussion.route
        ) {
            CreateDiscussion()
        }
    }
}