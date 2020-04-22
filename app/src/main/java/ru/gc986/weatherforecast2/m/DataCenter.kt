package ru.gc986.simplenotebook.m

import android.content.Context
import ru.base.dataprovider.sharPref.SharedPreferencesHelper
import ru.base.dataprovider.sharPref.SharedPreferencesHelperImpl
import ru.gc986.dataproviders.db.DB
import ru.gc986.dataproviders.db.DBI
import ru.gc986.dataproviders.net.RequestsI
import ru.gc986.dataproviders.net.RequestsImpl
import ru.gc986.weatherforecast.BuildConfig

class DataCenter(private val context: Context): DataCenterI {

    private var requests: RequestsImpl? = null
    private var mainServerUrl: String? = null
    private val sharedPref: SharedPreferencesHelper = SharedPreferencesHelperImpl(context)
    private val db: DBI = DB(context)

    init {
        setMainServerUrl(BuildConfig.MAINSERVER)
    }

    override fun setMainServerUrl(mainServer: String) {
        if (mainServer.isEmpty())
            throw NullPointerException("Url server must not be null")
        val fullMainServerUrl = if (mainServer.last().toString() != "/")
            "$mainServer/"
        else
            mainServer
        mainServerUrl = fullMainServerUrl
        requests?.setMainServer(fullMainServerUrl)
    }

    private fun getMainServerUrl(): String = mainServerUrl?:""

    private fun getRequests(): RequestsImpl = requests ?: let {
        val newRequests = RequestsImpl(context, BuildConfig.DEBUG)
        newRequests.setMainServer(getMainServerUrl())
        requests = newRequests
        return newRequests
    }

    override fun getNetProvider(): RequestsI = getRequests()

    override fun getSharedPref(): SharedPreferencesHelper = sharedPref

    override fun getDB(): DBI = db

}