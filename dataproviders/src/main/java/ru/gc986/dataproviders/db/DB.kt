package ru.gc986.dataproviders.db

import android.content.Context
import androidx.room.Room
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.gc986.models.weather.Weather

class DB(context: Context) : DBI {

    private val DB_NAME = "APP_DB.DB"
    val db: AppDB

    init {
        db = Room.databaseBuilder(context, AppDB::class.java, DB_NAME).build()
    }

    override fun insertWeather(weather: Weather): Single<Weather> = Single.create<Weather> {
            db.getWeatherDao().insert(weather)
            it.onSuccess(weather)
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())


    override fun getAllWeathers(): Single<List<Weather>> = Single.create<List<Weather>> {
            it.onSuccess(db.getWeatherDao().getAll())
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())

}