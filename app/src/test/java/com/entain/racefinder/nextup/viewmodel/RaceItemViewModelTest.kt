package com.entain.racefinder.nextup.viewmodel

import org.junit.After
import org.junit.Before
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RaceItemViewModelTest {

    @InjectMocks
    private lateinit var viewModel: RaceItemViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
    }
    @Test
    fun `startCountdown updates countdown correctly`() = runTest {
        val raceId = "race1"
        val initialTimeInMillis = 2000L
        viewModel.createFlow(raceId, initialTimeInMillis)
        viewModel.startCountdown(raceId, initialTimeInMillis) {}
        advanceTimeBy(1000)
        val countdownValue = viewModel.countdowns[raceId]?.value
        Assert.assertEquals(2000L, countdownValue)
    }

    @Test
    fun `startCountdown calls onRaceFinished when countdown reaches zero`() = runTest {
        // Arrange
        val raceId = "race1"
        val initialTimeInMillis = 2000L
        viewModel.createFlow(raceId, initialTimeInMillis)
        viewModel.startCountdown(raceId, initialTimeInMillis) {
            val countdownValue = viewModel.countdowns[raceId]?.value
            Assert.assertEquals(0L, countdownValue)
        }
        advanceTimeBy(2000)
    }

    @Test
    fun `startCountdown does not restart if already started`() = runTest {
        val raceId = "race1"
        val initialTimeInMillis = 5000L
        viewModel.createFlow(raceId, initialTimeInMillis)
        viewModel.startCountdown(raceId, initialTimeInMillis) {}
        advanceTimeBy(2000)
        viewModel.startCountdown(raceId, initialTimeInMillis) {}
        val countdownValue = viewModel.countdowns[raceId]?.value
        Assert.assertEquals(4000L, countdownValue)
    }
}
