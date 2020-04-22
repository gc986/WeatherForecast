package ru.gc986.dataproviders.net

import android.content.Context
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.gc986.dataproviders.net.common.BaseRequest
import ru.gc986.models.user.User
import ru.gc986.models.weather.Weather

class RequestsImpl(context: Context, showDebugInfo: Boolean) : BaseRequest(context, showDebugInfo),
    RequestsI {

    override fun getWeather(
        lat: String,
        lon: String,
        appid: String): Single<Weather> =
        bnc.getWeather(lat, lon, appid)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

}