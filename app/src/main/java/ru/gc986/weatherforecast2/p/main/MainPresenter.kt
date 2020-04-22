package ru.gc986.weatherforecast2.p.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.gc986.simplenotebook.m.DataCenterI
import ru.gc986.weatherforecast.BuildConfig
import ru.gc986.weatherforecast2.WeatherForecastApp.Companion.diData
import javax.inject.Inject

@InjectViewState
class MainPresenter: MvpPresenter<MainViewI>() {

    @Inject lateinit var dataCenter: DataCenterI
    private val unsubscribe = CompositeDisposable()

    init {
        diData.inject(this)
    }

    fun getWeather(){
        dataCenter.getNetProvider().getWeather("56.50", "60.35", BuildConfig.APPID)
            .subscribe({

            },{
                it.printStackTrace()
            })
            .addToUnsubcribe()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun Disposable.addToUnsubcribe() = unsubscribe.add(this)


}