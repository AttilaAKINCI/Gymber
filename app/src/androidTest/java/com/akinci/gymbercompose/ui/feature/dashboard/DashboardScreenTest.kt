package com.akinci.gymbercompose.ui.feature.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.akinci.gymbercompose.ui.feature.dashboard.view.DashboardScreen
import com.akinci.gymbercompose.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
@HiltAndroidTest
class DashboardScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            DashboardScreen(
                onNavigateToDetail = { },
                animationCount = 1
            )
        }
    }

    @Test
    fun dashboard_check_content() {

    }
}