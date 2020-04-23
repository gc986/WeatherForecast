package ru.gc986.commontools

import ru.gc986.commontools.Formats.Companion.roundToOneDecimalPlace

class Temperature {

    companion object{

        private val ABSOLUTE_ZERO_TEMPERATURE = "-273.15".toDouble()

        fun kelvinsToCelsius(kelvin:Double): Double = roundToOneDecimalPlace(kelvin + ABSOLUTE_ZERO_TEMPERATURE)

    }

}