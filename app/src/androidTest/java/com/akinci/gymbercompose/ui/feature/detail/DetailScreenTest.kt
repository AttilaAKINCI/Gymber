package com.akinci.gymbercompose.ui.feature.detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.akinci.gymbercompose.ui.feature.detail.view.DetailScreen
import com.akinci.gymbercompose.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
@HiltAndroidTest
class DetailScreenTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalUnitApi
    @Before
    fun setUp() {
        composeTestRule.setContent {
           DetailScreen(onBackPressed = { })
        }
    }

    @Test
    fun detail_check_content() {

    }
}