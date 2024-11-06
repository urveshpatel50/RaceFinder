package com.entain.racefinder.nextup.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorView_displaysErrorMessageAndTryAgainButton() {
        val errorMessage = "Something went wrong"
        var tryAgainClicked = false

        composeTestRule.setContent {
            ErrorView(errorMessage = errorMessage, onTryAgain = { tryAgainClicked = true })
        }

        composeTestRule.onNodeWithText("Error: $errorMessage")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Try Again")
            .assertIsDisplayed()
            .performClick()

        assert(tryAgainClicked)
    }
}
