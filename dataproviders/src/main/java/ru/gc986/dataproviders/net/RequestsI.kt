package ru.gc986.dataproviders.net

import io.reactivex.Single
import ru.gc986.models.weather.Weather

interface RequestsI {

    fun getWeather(
        lat: Double,
        lon: Double,
        appid: String,
        lang: String?)
            : Single<Weather>

}