package com.entain.racefinder.nextup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.entain.racefinder.nextup.ui.screen.RaceScreen
import com.entain.racefinder.nextup.ui.theme.RacingAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RacingAppTheme  {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RaceScreen()
                }
            }
        }
    }
}
