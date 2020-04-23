package ru.gc986.weatherforecast2

import android.app.Application
import android.os.Build
import ru.gc986.weatherforecast2.di.DIData
import ru.gc986.weatherforecast2.di.DIDataModule
import ru.gc986.weatherforecast2.di.DaggerDIData

class WeatherForecastApp: Application() {

    companion object{
        internal lateinit var diData: DIData
        private lateinit var instance:WeatherForecastApp

        fun getLocal() =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    instance.getResources().getConfiguration().locales
                else
                    instance.getResources().getConfiguration().locale
    }

    init {
        instance = this
        diData = buildData()
    }

    private fun buildData(): DIData {
        return DaggerDIData.builder()
            .dIDataModule(DIDataModule(this)).build()
    }

}