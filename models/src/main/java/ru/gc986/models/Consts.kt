package ru.gc986.models

class Consts {

    companion object {
        const val MAINSERVER = "MAINSERVER"
        const val SERVERADDRESS = "SERVERADDRESS"

        const val DEFAULT_INT_SP = 0
        const val EDITTEXT_DEBOUNCE = 1000L

        const val USER = "USER"
        const val SP_PAST_UPDATE_TIME = "SP_PAST_UPDATE_TIME"
        const val ONE_SEC_IN_MILLISECONDS = 60000L
    }

    enum class Locals(val system:String, val short: String){
        EN("[en_US]","en"),
        RU("[ru_RU]","ru")
    }

}