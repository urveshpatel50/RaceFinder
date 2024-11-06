package com.entain.racefinder.nextup.utils

import java.util.Locale
import java.util.concurrent.TimeUnit


object Util {

    fun getCountryName(countryCode: String): String {
        val locale = Locale("", countryCode.uppercase())
        return locale.displayCountry
    }
}