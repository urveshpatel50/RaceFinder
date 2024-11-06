package com.entain.racefinder.nextup.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(errorMessage: String?, onTryAgain: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Error: $errorMessage", color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onTryAgain() }) {
            Text("Try Again")
        }
    }
}