package com.entain.racefinder.nextup.data.model

import com.google.gson.annotations.SerializedName

data class RaceSummary(
    @SerializedName("meeting_name") val meetingName: String,
    @SerializedName("race_number") val raceNumber: Int,
    @SerializedName("advertised_start") val advertisedStart: AdvertisedStart,
    @SerializedName("venue_country") val countryCode: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("race_id") val id:String

)
