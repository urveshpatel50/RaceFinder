package com.entain.racefinder.nextup.data.repository

import android.util.Log
import com.entain.racefinder.nextup.data.api.RacingApiService
import com.entain.racefinder.nextup.data.model.RaceSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class RacingRepository @Inject constructor(
    private val apiService: RacingApiService
) {
    @Throws(Exception::class)
    fun fetchRaces(categoryFilter: Set<String>): Flow<List<RaceSummary>> = flow {
        val response = withContext(Dispatchers.IO) {
            apiService.getNextRaces()
        }

        if (response.status != 200) {
            throw Exception("Failed to fetch races: ${response.message}") //Any specific error from backend should be handled here
        }

        val data = response.data ?: throw Exception("No races are found!")

        val filteredRaces = data.raceSummaries.values
            .filter { it.categoryId in categoryFilter && !isRaceOver(it.advertisedStart.seconds) }
            .sortedBy { it.advertisedStart.seconds }
            .take(5)

        emit(filteredRaces)
    }.catch { exception ->
        Log.e("RacingRepository", "Error fetching races: ${exception.message}")
        throw exception
    }


    private fun isRaceOver(advertisedStartSeconds: Long): Boolean {
        val currentTime = System.currentTimeMillis() / 1000
        return advertisedStartSeconds < currentTime
    }
}

