package com.brandy.cooperativeapp.navigation

sealed class Route(val route:String) {
    data object LoginUI: Route(route = "login")
    data object HomeUi: Route(route = "home")
}