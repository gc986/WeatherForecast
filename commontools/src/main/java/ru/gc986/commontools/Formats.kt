package ru.gc986.commontools

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class Formats {

    companion object{

        fun roundToOneDecimalPlace(original: Double): Double {
            val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.ENGLISH)).apply {
                roundingMode = RoundingMode.HALF_UP
            }
            return df.format(original).toDouble()
        }

    }

}