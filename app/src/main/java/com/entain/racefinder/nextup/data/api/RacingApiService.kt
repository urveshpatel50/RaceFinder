package com.entain.racefinder.nextup.data.api

import com.entain.racefinder.nextup.data.model.RaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RacingApiService {
    @GET("racing/")
    suspend fun getNextRaces(
        @Query("method") method: String = "nextraces",
        @Query("count") count: Int = 10
    ): RaceResponse
}
