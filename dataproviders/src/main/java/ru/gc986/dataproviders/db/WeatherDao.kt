package ru.gc986.dataproviders.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.gc986.models.weather.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: Weather)

    @Query("SELECT * FROM Weather")
    fun getAll(): List<Weather>

}