package ru.gc986.models

class Consts {

    companion object {
        const val DEFAULT_INT_SP = 0

        const val ONE_SEC_IN_MILLISECONDS = 1000L
        const val ONE_MIN_IN_SECONDS = 60L
        const val TEN_MINUTES = 10L * ONE_MIN_IN_SECONDS * ONE_SEC_IN_MILLISECONDS

        val SEPARATOR = "&"
        val LIST_SEPARATOR = "§"
    }

    enum class Locals(val system:String, val short: String){
        EN("[en_US]","en"),
        RU("[ru_RU]","ru")
    }

}