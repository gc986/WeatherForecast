package ru.base.dataprovider.sharPref

import io.reactivex.Observable
import io.reactivex.Single

interface SharedPreferencesHelper {

    fun putSPString(name: String, value: String): Single<String>
    fun putSPInt(name: String, value: Int): Single<Int>
    fun putSPLong(name: String, value: Long): Single<Long>
    fun putSPBoolean(name: String, value: Boolean): Single<Boolean>
    fun getSPString(name: String): Observable<String>
    fun getSPInt(name: String): Observable<Int>
    fun getSPInt(name: String, defValue: Int): Observable<Int>
    fun getSPLong(name: String): Observable<Long>
    fun getSPBoolean(name: String): Observable<Boolean>
    fun getSPBoolean(name: String, defaultValue: Boolean): Observable<Boolean>

}