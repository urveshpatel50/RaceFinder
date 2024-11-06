package com.entain.racefinder.nextup.ui.components

import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.entain.racefinder.nextup.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoadingViewTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun loadingView_displaysCircularProgressIndicator() {
        // Set up the UI with the LoadingView composable
        composeTestRule.activity.setContent {
            LoadingView()

            composeTestRule.onNodeWithContentDescription("Loading")
                .assertIsNotDisplayed()

            // Verify that the CircularProgressIndicator is displayed
            composeTestRule.onNodeWithContentDescription("Loading")
                .assertIsDisplayed()
        }
    }
}