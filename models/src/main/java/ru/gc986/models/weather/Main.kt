package ru.gc986.models.weather

import java.io.Serializable

data class Main(
    val feels_like: Double,
    val humidity: Double,
    val pressure: Double,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
): Serializable