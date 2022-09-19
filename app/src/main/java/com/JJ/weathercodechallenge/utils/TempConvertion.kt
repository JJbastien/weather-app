package com.JJ.weathercodechallenge.utils

import java.util.*
import kotlin.math.roundToInt

fun kelvinToFahrenheit(kelvinTemp: Double): String {
    // (287.88 K − 273.15) × 9/5 + 32 = 58.514 °F
    val subsTemp = kelvinTemp - 273.15
    val multiConvert = subsTemp * (9/5)
    val  result = multiConvert + 32

    return result.roundToInt().toString()
}

fun feelLikeTemp(kelvinTemp: Double) =
    String.format(Locale.getDefault(), "Feels Like: " + kelvinToFahrenheit(kelvinTemp))

fun displayTemp(kelvinTemp: Double): String =
    String.format(Locale.getDefault(), "Temp: " + kelvinToFahrenheit(kelvinTemp))