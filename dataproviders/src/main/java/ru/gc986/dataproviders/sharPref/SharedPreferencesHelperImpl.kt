package ru.base.dataprovider.sharPref

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.Single
import ru.gc986.models.Consts.Companion.DEFAULT_INT_SP

class SharedPreferencesHelperImpl(private val context: Context) : SharedPreferencesHelper {

    private val PREF_NAME_SHARED_PREF_M_POS = "PREF_NAME_SHARED_PREF_M_POS"
    private val mSettings: SharedPreferences = context.getSharedPreferences(PREF_NAME_SHARED_PREF_M_POS, Context.MODE_PRIVATE)

    override fun putSPString(name: String, value: String): Single<String> {
        return Single.create {
            val editor: SharedPreferences.Editor = mSettings.edit()
            editor.putString(name, value)
            editor.apply()
            it.onSuccess(value)
        }
    }

    override fun putSPInt(name: String, value: Int): Single<Int> {
        return Single.create {
            val editor: SharedPreferences.Editor = mSettings.edit()
            editor.putInt(name, value)
            editor.apply()

            it.onSuccess(value)
        }
    }

    override fun putSPLong(name: String, value: Long): Single<Long> {
        return Single.create {
            val editor: SharedPreferences.Editor = mSettings.edit()
            editor.putLong(name, value)
            editor.apply()

            it.onSuccess(value)
        }
    }

    override fun putSPBoolean(name: String, value: Boolean): Single<Boolean> {
        return Single.create {
            val editor: SharedPreferences.Editor = mSettings.edit()
            editor.putBoolean(name, value)
            editor.apply()

            it.onSuccess(value)
        }
    }

    override fun getSPString(name: String): Observable<String> {
        return Observable.create {
            it.onNext(mSettings.getString(name, "")?:"")
            it.onComplete()
        }
    }

    override fun getSPInt(name: String): Observable<Int> {
        return Observable.create {
            it.onNext(mSettings.getInt(name, DEFAULT_INT_SP))
            it.onComplete()
        }
    }

    override fun getSPInt(name: String, defValue: Int): Observable<Int> = Observable.create {
        it.onNext(mSettings.getInt(name, defValue))
        it.onComplete()
    }

    override fun getSPLong(name: String): Observable<Long> {
        return Observable.create {
            it.onNext(mSettings.getLong(name, 0))
            it.onComplete()
        }
    }

    override fun getSPBoolean(name: String) = getSPBoolean(name, false)

    override fun getSPBoolean(name: String, defaultValue: Boolean): Observable<Boolean> =
        Observable.create {
            it.onNext(mSettings.getBoolean(name, defaultValue))
            it.onComplete()
        }
}