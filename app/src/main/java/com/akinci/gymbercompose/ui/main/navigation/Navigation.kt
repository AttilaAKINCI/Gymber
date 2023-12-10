package com.akinci.gymbercompose.ui.main.navigation

/**
 * For parametered navigation define create route function.
 * **/

sealed class Navigation(val route: String){
    object Splash: Navigation("splash")
    object Dashboard: Navigation("dashboard")
    object Detail: Navigation("detail")

    open fun createRoute(args: Map<String, String>?): String{ return route }
}