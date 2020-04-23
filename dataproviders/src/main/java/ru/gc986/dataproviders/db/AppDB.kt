package ru.gc986.dataproviders.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.gc986.dataproviders.db.converters.Converters
import ru.gc986.models.weather.Weather

@Database(entities = [Weather::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDB: RoomDatabase() {

    abstract fun getWeatherDao():WeatherDao

}