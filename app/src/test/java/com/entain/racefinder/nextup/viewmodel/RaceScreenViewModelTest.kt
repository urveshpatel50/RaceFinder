package com.entain.racefinder.nextup.viewmodel

import app.cash.turbine.test
import com.entain.racefinder.nextup.data.model.AdvertisedStart
import com.entain.racefinder.nextup.data.model.Race
import com.entain.racefinder.nextup.data.model.RaceSummary
import com.entain.racefinder.nextup.data.repository.RacingRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class,
    ExperimentalTime::class)
@RunWith(MockitoJUnitRunner::class)
class RaceScreenViewModelTest {

    @InjectMocks
    private lateinit var viewModel: RaceScreenViewModel

    @Mock
    private lateinit var repository: RacingRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
    }

    @Test
    fun `startRaceUpdates emits race summaries`() = runTest {
        val raceSummaries = listOf(
            RaceSummary(
                "1", 1, AdvertisedStart(1000L), "AU", Race.HORSE_RACING.categoryId, "Race 1"
            ),
            RaceSummary(
                "2", 2, AdvertisedStart(4000L), "UK", Race.HARNESS_RACING.categoryId, "Race 2"
            )
        )

        `when`(repository.fetchRaces(anySet())).thenReturn(flowOf(raceSummaries))
        viewModel.startRaceUpdates(false)

        viewModel.races.test {

            assertEquals(raceSummaries, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `startRaceUpdates handles error properly`() = runTest {

        val errorMessage = "Network Error"
        `when`(repository.fetchRaces(anySet())).thenThrow(Exception(errorMessage))

        viewModel.refreshRaces()
        viewModel.errorMessage.test {
            assertEquals(null, awaitItem())

            assertEquals(errorMessage, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `test update filter updates filterFlow`() = runTest {
        val newFilters = setOf(Race.HARNESS_RACING.categoryId)
        viewModel.updateFilter(newFilters)
        assertEquals(newFilters, viewModel.filterFlow.value)
    }
}
