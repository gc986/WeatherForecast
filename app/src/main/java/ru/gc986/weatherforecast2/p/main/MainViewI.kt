package ru.gc986.weatherforecast2.p.main

import com.arellomobile.mvp.MvpView
import ru.gc986.models.weather.Weather

interface MainViewI: MvpView {

    fun showMessage(message: String)
    fun weatherUpdatesInProgress()
    fun weatherUpdatesStopProgress()
    fun onNewWeather(weather: Weather, iconPath: String)

}