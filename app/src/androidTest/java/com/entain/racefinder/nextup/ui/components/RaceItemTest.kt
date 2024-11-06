package com.entain.racefinder.nextup.ui.components
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.entain.racefinder.nextup.MainActivity
import com.entain.racefinder.nextup.data.model.AdvertisedStart
import com.entain.racefinder.nextup.data.model.RaceSummary
import com.entain.racefinder.nextup.data.model.Race
import com.entain.racefinder.nextup.viewmodel.RaceItemViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class RaceItemTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var raceSummary: RaceSummary

    @Before
    fun setUp() {
        hiltRule.inject()
        raceSummary = RaceSummary(
            categoryId = Race.HORSE_RACING.categoryId,
            meetingName = "Royal Ascot",
            raceNumber = 1,
            countryCode = "UK",
            advertisedStart = AdvertisedStart(TimeUnit.MINUTES.toSeconds(30)), // assuming seconds, 30 minutes from now
            id = "Race 1"
        )
    }

    @Test
    fun raceItemDisplaysCorrectInfo() {

        composeTestRule.activity.setContent {
            RaceItem(raceSummary = raceSummary, onRaceFinished = {})
        }

        composeTestRule.onNodeWithText("Royal Ascot R1").assertIsDisplayed()
        composeTestRule.onNodeWithText("United Kingdom").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Horse Racing Icon").assertExists()
    }

    @Test
    fun testCountdownUpdates()  {
        val viewModel = RaceItemViewModel()

        viewModel.createFlow(raceSummary.id,
            TimeUnit.MINUTES.toSeconds(5) * 1000)

        composeTestRule.activity.setContent {
            RaceItem(viewModel, raceSummary = raceSummary, onRaceFinished = {})

            composeTestRule.onNodeWithText("0").assertIsNotDisplayed()
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithText("5m").assertIsDisplayed() // Initially 5 minutes
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithText("4m").assertIsDisplayed()  // After 1 second
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithText("3m").assertIsDisplayed()
        }
    }
}
