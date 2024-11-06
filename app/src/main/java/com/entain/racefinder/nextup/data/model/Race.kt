package com.entain.racefinder.nextup.data.model

import androidx.annotation.DrawableRes
import com.entain.racefinder.R

enum class Race(val categoryId: String,
                        val label: String,
                        @DrawableRes val icon: Int) {

    HORSE_RACING("4a2788f8-e825-4d36-9894-efd4baf1cfae","Horse Racing", R.drawable.ic_horse_racing),
    GREYHOUND_RACING("9daef0d7-bf3c-4f50-921d-8e818c60fe61", "Greyhound Racing", R.drawable.ic_greyhound_racing),
    HARNESS_RACING("161d9be2-e909-4326-8c2c-35ed71fb460b", "Harness Racing", R.drawable.ic_harness_racing);

    companion object {
        fun matches(categoryId: String) = Race.entries.find { it.categoryId == categoryId }
    }
}