package com.imtuc.talknity.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.imtuc.talknity.view.*
import com.imtuc.talknity.viewmodel.AuthViewModel
import com.imtuc.talknity.viewmodel.HomeViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    lifecycleOwner: LifecycleOwner
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen()
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
            CommunityCategories()
        }
    }
}