package com.entain.racefinder.nextup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.entain.racefinder.nextup.data.model.Race
import com.entain.racefinder.nextup.ui.theme.Gray
import com.entain.racefinder.nextup.ui.theme.Orange
import com.entain.racefinder.nextup.viewmodel.RaceScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun FilterOptions(viewModel: RaceScreenViewModel) {
    var horseRacingChecked by remember { mutableStateOf(true) }
    var harnessRacingChecked by remember { mutableStateOf(true) }
    var greyhoundRacingChecked by remember { mutableStateOf(true) }

    fun updateFilters() {
        val selectedFilters = mutableSetOf<String>()
        if (horseRacingChecked) selectedFilters.add(Race.HORSE_RACING.categoryId)
        if (harnessRacingChecked) selectedFilters.add(Race.HARNESS_RACING.categoryId)
        if (greyhoundRacingChecked) selectedFilters.add(Race.GREYHOUND_RACING.categoryId)

        if (selectedFilters.isEmpty()) {
            horseRacingChecked = true
            harnessRacingChecked = true
            greyhoundRacingChecked = true
            selectedFilters.add(Race.HORSE_RACING.categoryId)
            selectedFilters.add(Race.HARNESS_RACING.categoryId)
            selectedFilters.add(Race.GREYHOUND_RACING.categoryId)
        }

        viewModel.updateFilter(selectedFilters)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        var race = Race.HORSE_RACING
        FilterItem(
            label = race.label,
            checked = horseRacingChecked,
            onCheckedChange = {
                horseRacingChecked = it
                updateFilters()
            },
            iconResId = race.icon
        )

        race = Race.GREYHOUND_RACING
        FilterItem(
            label = race.label,
            checked = greyhoundRacingChecked,
            onCheckedChange = {
                greyhoundRacingChecked = it
                updateFilters()
            },
            iconResId = race.icon
        )

        race = Race.HARNESS_RACING
        FilterItem(
            label = race.label,
            checked = harnessRacingChecked,
            onCheckedChange = {
                harnessRacingChecked = it
                updateFilters()
            },
            iconResId = race.icon
        )
    }
}

@Composable
fun FilterItem(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, iconResId: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (checked) MaterialTheme.colorScheme.primary else Color.Transparent)
                .border(
                    width = 2.dp,
                    color = if (checked) MaterialTheme.colorScheme.primary else Gray,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(4.dp)
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Transparent,
                    uncheckedColor = Color.Transparent,
                    checkmarkColor = MaterialTheme.colorScheme.background
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "$label Icon",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )
    }
}