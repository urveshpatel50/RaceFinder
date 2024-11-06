package com.entain.racefinder.nextup.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.entain.racefinder.nextup.data.model.Race
import com.entain.racefinder.nextup.data.model.RaceSummary
import com.entain.racefinder.nextup.data.model.formatDuration
import com.entain.racefinder.nextup.data.model.remainingTimeInMillis
import com.entain.racefinder.nextup.utils.Util
import com.entain.racefinder.nextup.viewmodel.RaceItemViewModel

@Composable
fun RaceItem(viewModel : RaceItemViewModel = hiltViewModel(), raceSummary: RaceSummary, onRaceFinished: () -> Unit) {

    val raceId = raceSummary.id
    val initialCountdown = raceSummary.advertisedStart.seconds.remainingTimeInMillis()

    val countdown = viewModel.createFlow(raceId, initialTimeInMillis = initialCountdown)?.collectAsState()?.value

    LaunchedEffect(raceId) {
        viewModel.startCountdown(raceId, initialCountdown, onRaceFinished)
    }

    Row(modifier = Modifier.padding(4.dp).fillMaxWidth()) {
        val race = Race.matches(raceSummary.categoryId)

        race?.let {
            Icon(
                painter = painterResource(id = it.icon),
                contentDescription = "${it.label} Icon",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp).testTag("${it.label} Icon")
            )
        }
        Spacer(modifier = Modifier.width(4.dp))

        Column(modifier = Modifier.padding(4.dp).weight(0.8f)) {
            Text(
                text = "${raceSummary.meetingName} R${raceSummary.raceNumber}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = Util.getCountryName(raceSummary.countryCode),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.weight(0.2f))

        countdown?.let {
            Text(
                text = it.formatDuration(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}