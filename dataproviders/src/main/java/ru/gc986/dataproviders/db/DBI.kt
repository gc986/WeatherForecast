package ru.gc986.dataproviders.db

import io.reactivex.Observable
import io.reactivex.Single
import ru.gc986.models.weather.Weather

interface DBI {

    fun insertWeather(users: Weather): Single<Weather>

    fun getAllWeathers(): Single<List<Weather>>

}