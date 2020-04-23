package ru.gc986.weatherforecast2.p.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.gc986.models.Consts
import ru.gc986.models.weather.Weather
import ru.gc986.simplenotebook.m.DataCenterI
import ru.gc986.weatherforecast.BuildConfig
import ru.gc986.weatherforecast2.WeatherForecastApp
import ru.gc986.weatherforecast2.WeatherForecastApp.Companion.diData
import javax.inject.Inject

@InjectViewState
class MainPresenter: MvpPresenter<MainViewI>() {

    @Inject lateinit var dataCenter: DataCenterI
    private val unsubscribe = CompositeDisposable()

    init {
        diData.inject(this)
    }

    fun getWeather(longitude: Double, latitude: Double){
        dataCenter.getNetProvider().getWeather(latitude, longitude, BuildConfig.APPID, getLocal()).toObservable()
            .doOnNext { it.time = System.currentTimeMillis() }
            .flatMap { dataCenter.getDB().insertWeather(it).toObservable() }
            .doOnSubscribe { viewState.weatherUpdatesInProgress() }
            .doFinally { viewState.weatherUpdatesStopProgress() }
            .subscribe({
                viewState.onNewWeather(it, getPathOfWeatherIcon(it))
            },{
                it.printStackTrace()
            })
            .addToUnsubcribe()
    }

    private fun getPathOfWeatherIcon(weather: Weather):String = "http://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"

    private fun getLocal():String?{
        val local = WeatherForecastApp.getLocal()

        Consts.Locals.values().forEach {
            if (it.system==local.toString())
                return it.short
        }

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribe.dispose()
    }

    fun Disposable.addToUnsubcribe() = unsubscribe.add(this)


}