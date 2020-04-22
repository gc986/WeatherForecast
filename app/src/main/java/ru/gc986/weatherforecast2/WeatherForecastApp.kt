package ru.gc986.weatherforecast2

import android.app.Application
import ru.gc986.weatherforecast2.di.DIData
import ru.gc986.weatherforecast2.di.DIDataModule
import ru.gc986.weatherforecast2.di.DaggerDIData

class WeatherForecastApp: Application() {

    companion object{
        internal lateinit var diData: DIData
    }

    init {
        diData = buildData()
    }

    private fun buildData(): DIData {
        return DaggerDIData.builder()
            .dIDataModule(DIDataModule(this)).build()
    }

}