package ru.gc986.dataproviders.net

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.gc986.models.user.User
import ru.gc986.models.weather.Weather

interface Requests {

    @GET("data/2.5/weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("lang") lang: String?)
            :Single<Weather>

}