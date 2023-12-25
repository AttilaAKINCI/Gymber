package com.akinci.gymber.ui.features.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.akinci.gymber.ui.ds.components.SystemBarColorController
import com.akinci.gymber.ui.features.NavGraphs
import com.akinci.gymber.ui.features.appCurrentDestinationAsState
import com.akinci.gymber.ui.features.startAppDestination
import com.akinci.gymber.ui.navigation.getSystemBarColors
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navHostEngine = rememberAnimatedNavHostEngine()

    val destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    // for each screen, we need to adjust system bars color & states
    SystemBarColorController(systemBarColors = destination.getSystemBarColors)

    DestinationsNavHost(
        navController = navController,
        engine = navHostEngine,
        navGraph = NavGraphs.root,
        startRoute = NavGraphs.root.startRoute,
    )
}