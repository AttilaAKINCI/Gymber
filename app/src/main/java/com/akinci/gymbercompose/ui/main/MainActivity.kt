package com.akinci.gymbercompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.akinci.gymbercompose.ui.feature.dashboard.view.DashboardScreen
import com.akinci.gymbercompose.ui.feature.dashboard.viewmodel.DashboardViewModel
import com.akinci.gymbercompose.ui.feature.detail.view.DetailScreen
import com.akinci.gymbercompose.ui.feature.splash.view.SplashScreen
import com.akinci.gymbercompose.ui.main.navigation.Navigation
import com.akinci.gymbercompose.ui.main.util.GymberAppState
import com.akinci.gymbercompose.ui.main.util.rememberGymberAppState
import com.akinci.gymbercompose.ui.theme.GymberComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @ExperimentalUnitApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymberApp()
        }
    }
}

@ExperimentalUnitApi
@ExperimentalAnimationApi
@Composable
fun GymberApp(
    appState: GymberAppState = rememberGymberAppState()
){
    GymberComposeTheme {
        MainNavHost(appState)
    }
}

@ExperimentalUnitApi
@ExperimentalAnimationApi
@Composable
fun MainNavHost(
    appState: GymberAppState,
    modifier: Modifier = Modifier
){
    /** dashboardViewModel is used as sharedViewModel between Dashboard Screen and Detail Screen **/
    val sharedViewModel: DashboardViewModel = hiltViewModel()

    NavHost(
        navController = appState.navController,
        startDestination = Navigation.Splash.route,
        modifier = modifier
    ){
        composable(route = Navigation.Splash.route){
            SplashScreen(onTimeout = { appState.navigate(Navigation.Dashboard, from = it) })
        }
        composable(route = Navigation.Dashboard.route){
            DashboardScreen(
                viewModel = sharedViewModel,
                onNavigateToDetail = { appState.navigate(Navigation.Detail, from = it) }
            )
        }
        composable(route = Navigation.Detail.route){
            DetailScreen(
                viewModel = sharedViewModel,
                onBackPressed = { appState.navigateBack() }
            )
        }
    }
}