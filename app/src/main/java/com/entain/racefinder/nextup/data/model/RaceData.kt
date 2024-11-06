package com.entain.racefinder.nextup.data.model

import com.google.gson.annotations.SerializedName

data class RaceData(
    @SerializedName("next_to_go_ids") val nextToGoIds: List<String>,
    @SerializedName("race_summaries") val raceSummaries: Map<String, RaceSummary>
)
