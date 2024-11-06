package com.entain.racefinder.nextup.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.entain.racefinder.nextup.data.model.RaceSummary
import com.entain.racefinder.nextup.ui.components.ErrorView
import com.entain.racefinder.nextup.ui.components.FilterOptions
import com.entain.racefinder.nextup.ui.components.LoadingView
import com.entain.racefinder.nextup.ui.components.RaceItem
import com.entain.racefinder.nextup.viewmodel.RaceScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun RaceScreen(viewModel: RaceScreenViewModel = hiltViewModel()) {
    val races by viewModel.races.collectAsState(initial = emptyList())
    val isLoading by viewModel.loading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.startRaceUpdates(true)
    }

    Column(Modifier.fillMaxHeight()) {
        TopAppBar(
            title = {
                Text(text = "Next Races")
            }
        )

        FilterOptions(viewModel)

        when {
            isLoading -> {
                LoadingView()
            }

            errorMessage != null -> {
                ErrorView(errorMessage) { viewModel.refreshRaces() }
            }

            races.isEmpty() -> {
                Text("No races found.", modifier = Modifier.padding(16.dp))
            }

            else -> {
                RaceListView(races, viewModel)
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun RaceListView(races: List<RaceSummary>, viewModel: RaceScreenViewModel) {
    LazyColumn {
        items(races.size) { index ->
            RaceItem(raceSummary = races[index]) {
                viewModel.refreshRaces()
            }
            if (index < races.lastIndex) {
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
