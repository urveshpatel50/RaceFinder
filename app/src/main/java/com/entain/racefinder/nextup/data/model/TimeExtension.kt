package com.entain.racefinder.nextup.data.model

import java.util.concurrent.TimeUnit


fun Long.remainingTimeInMillis() : Long {
    return (this * 1000) - System.currentTimeMillis()
}

fun Long.formatDuration(): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this)

    return when {
        seconds >= 300 -> {
            // 5 minutes or more
            "${seconds / 60}m"
        }
        seconds >= 60 -> {
            // Between 1 and 5 minutes
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            "${minutes}m ${remainingSeconds}s"
        }
        else -> {
            // Less than 1 minute
            "${seconds}s"
        }
    }
}