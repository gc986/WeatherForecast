package ru.gc986.dataproviders.db.converters

import androidx.room.TypeConverter
import ru.gc986.models.Consts
import ru.gc986.models.Consts.Companion.LIST_SEPARATOR
import ru.gc986.models.Consts.Companion.SEPARATOR
import ru.gc986.models.weather.Coord
import ru.gc986.models.weather.Main
import ru.gc986.models.weather.WeatherX

class Converters {

    @TypeConverter
    fun fromCoord(coord: Coord): String =
        "${coord.lat}$SEPARATOR${coord.lon}"

    @TypeConverter
    fun toCoord(data: String): Coord =
        Coord(
            data.split(SEPARATOR)[0].toDouble(),
            data.split(",")[1].toDouble()
        )

    @TypeConverter
    fun fromMain(main: Main): String =
        "${main.feels_like}$SEPARATOR" +
                "${main.humidity}$SEPARATOR" +
                "${main.pressure}$SEPARATOR" +
                "${main.temp}$SEPARATOR" +
                "${main.temp_max}$SEPARATOR" +
                "${main.temp_min}"

    @TypeConverter
    fun toMain(data: String): Main =
        Main(data.split(SEPARATOR)[0].toDouble(),
            data.split(SEPARATOR)[1].toDouble(),
            data.split(SEPARATOR)[2].toDouble(),
            data.split(SEPARATOR)[3].toDouble(),
            data.split(SEPARATOR)[4].toDouble(),
            data.split(SEPARATOR)[5].toDouble())

    fun fromWeatherX(weatherX: WeatherX): String =
        "${weatherX.description}$SEPARATOR" +
                "${weatherX.icon}$SEPARATOR" +
                "${weatherX.id}$SEPARATOR" +
                "${weatherX.main}"

    fun toWeatherX(data: String): WeatherX =
        WeatherX(data.split(SEPARATOR)[0],
            data.split(SEPARATOR)[1],
            data.split(SEPARATOR)[2].toInt(),
            data.split(SEPARATOR)[3])

    @TypeConverter
    fun fromWeatherXList(list: List<WeatherX>): String {
        var out = ""
        list.forEach {
            if (out.isNotEmpty()) out += LIST_SEPARATOR
            out += fromWeatherX(it)
        }
        return out
    }

    @TypeConverter
    fun fromWeatherXList(data: String):List<WeatherX>{
        var out = ArrayList<WeatherX>()
        val datas = data.split(LIST_SEPARATOR)
        datas.forEach {
            out.add(toWeatherX(it))
        }

        return out
    }

}