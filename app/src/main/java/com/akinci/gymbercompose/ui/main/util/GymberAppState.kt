package com.akinci.gymbercompose.ui.main.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.akinci.gymbercompose.ui.main.navigation.Navigation

private fun NavBackStackEntry.lifecycleIsResumed() = this.lifecycle.currentState == Lifecycle.State.RESUMED

@Composable
fun rememberGymberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    GymberAppState(navController)
}

class GymberAppState(
    val navController: NavHostController
) {
    fun navigateBack() { navController.popBackStack() }

    fun navigate(navigationRoute: Navigation, args: Map<String,String>? = null, from: NavBackStackEntry){
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(navigationRoute.createRoute(args))
        }
    }
}