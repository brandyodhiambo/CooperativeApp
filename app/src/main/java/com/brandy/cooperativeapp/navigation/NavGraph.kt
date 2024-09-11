package com.brandy.cooperativeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brandy.cooperativeapp.presentation.auth.LoginScreen
import com.brandy.cooperativeapp.presentation.home.HomeScreen

@Composable
fun SetUpNavGraph(
    isLoggedIn:Boolean,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn) {
            Route.HomeUi.route
        } else{
            Route.LoginUI.route
        }
    ) {
        composable(
            route = Route.LoginUI.route
        ){
            LoginScreen(
                navController = navController
            )
        }

        composable(
            route = Route.HomeUi.route,
        ){
            HomeScreen(navController = navController)
        }
    }

}