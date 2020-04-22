package ru.gc986.simplenotebook.m

import ru.base.dataprovider.sharPref.SharedPreferencesHelper
import ru.gc986.dataproviders.db.DBI
import ru.gc986.dataproviders.net.RequestsI

interface DataCenterI {

    fun setMainServerUrl(mainServer: String)
    fun getNetProvider(): RequestsI
    fun getSharedPref(): SharedPreferencesHelper
    fun getDB(): DBI

}