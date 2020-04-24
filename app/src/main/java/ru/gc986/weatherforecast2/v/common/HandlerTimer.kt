package ru.gc986.weatherforecast2.v.common

import android.os.Handler
import ru.gc986.models.Consts.Companion.ONE_MIN_IN_SECONDS

class HandlerTimer {

    var runnable:Runnable? = null
    val handler = Handler()
    var timeSeconds = ONE_MIN_IN_SECONDS

    fun start( timeSeconds: Long?, onTick:OnTick) {
        timeSeconds?.let { this.timeSeconds = it }
        stop()
        runnable = Runnable {
            onTick()
            startDelay()
        }
        startDelay()
    }

    private fun startDelay() = handler.postDelayed(runnable, this.timeSeconds)


    fun stop() = handler.removeCallbacksAndMessages(null)

    fun reset() {
        if (runnable==null)
            return
        stop()
        startDelay()
    }

}