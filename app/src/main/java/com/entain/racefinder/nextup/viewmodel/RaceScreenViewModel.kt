package com.entain.racefinder.nextup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entain.racefinder.nextup.data.model.Race
import com.entain.racefinder.nextup.data.model.RaceSummary
import com.entain.racefinder.nextup.data.repository.RacingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RaceScreenViewModel @Inject constructor(private val repository: RacingRepository) : ViewModel() {
    private val raceCategories = setOf(
        Race.HORSE_RACING.categoryId,
        Race.GREYHOUND_RACING.categoryId,
        Race.HARNESS_RACING.categoryId
    )
    val filterFlow = MutableStateFlow(raceCategories)
    private val refreshIntervalMillis = 60_000L

    private val _races = MutableSharedFlow<List<RaceSummary>>(replay = 1)
    val races: SharedFlow<List<RaceSummary>> = _races

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun startRaceUpdates(repeat: Boolean = true) {
        viewModelScope.launch {
            filterFlow.collectLatest { filterSet ->
                do {
                    fetchRaces(filterSet)
                    if (repeat) delay(refreshIntervalMillis)
                } while (repeat)
            }
        }
    }

     private suspend fun fetchRaces(filterSet: Set<String>) {
        _loading.value = true
        try {
            repository.fetchRaces(filterSet).collect { raceList ->
                _loading.value = false
                _errorMessage.value = null // Reset error message
                if (raceList != _races.replayCache.lastOrNull()) { // Prevent emitting same data
                    _races.emit(raceList)
                }
            }
        } catch (e: Exception) {
            _loading.value = false
            _errorMessage.value = e.message // Set error message
        }
    }

    fun refreshRaces() {
        viewModelScope.launch {
            fetchRaces(filterFlow.value)
        }
    }

    fun updateFilter(filters: Set<String>) {
        filterFlow.value = filters.ifEmpty { raceCategories } // If empty, reset to all categories
    }
}
