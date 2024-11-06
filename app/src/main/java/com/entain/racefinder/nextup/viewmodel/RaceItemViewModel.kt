package com.entain.racefinder.nextup.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceItemViewModel @Inject public constructor() : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val countdowns = mutableMapOf<String, MutableStateFlow<Long>>()

    fun startCountdown(raceId: String, initialTimeInMillis: Long, onRaceFinished: () -> Unit) {
        viewModelScope.launch {
            var timeLeft = initialTimeInMillis
            while (timeLeft > 0) {
                delay(1000)
                timeLeft -= 1000
                countdowns[raceId]?.value = timeLeft

                if (timeLeft <= 0) {
                    onRaceFinished()
                    break
                }
            }
        }
    }

    fun createFlow(raceId: String, initialTimeInMillis: Long): MutableStateFlow<Long>? {
        if (!countdowns.containsKey(raceId)) {
            countdowns[raceId] = MutableStateFlow(initialTimeInMillis)
        }
        return countdowns[raceId]
    }
}