package ru.gc986.models.weather

data class Weather(
    val innerId: Long,
    val base: String,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherX>,
    val time: Long
)