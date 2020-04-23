package ru.gc986.weatherforecast2.v.common

import android.Manifest
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import ru.gc986.dialogs.Dialogs
import ru.gc986.models.weather.Weather
import ru.gc986.weatherforecast.R


class CheckPermissions(private val fragmentActivity: FragmentActivity) {

    fun toCheckPermissions(onCompleted: () -> Unit, onCancelled: ()-> Unit, vararg permissions:String){
        RxPermissions(fragmentActivity)
            .request(Manifest.permission.ACCESS_COARSE_LOCATION)
            .subscribe({ granded ->
                if (granded)
                    onCompleted.invoke()
                else {
                    Dialogs().showSimpleQuest(
                        context = fragmentActivity,
                        title = getString(R.string.not_enough_permissions),
                        description = getString(R.string.repeat_the_permission_request_),
                        isCancelable = false
                    ) { isOk ->
                        if (isOk)
                            toCheckPermissions( onCompleted, onCancelled, *permissions)//, onCompleted, onCancelled)
                        else
                            onCancelled.invoke()
                    }
                }
            }, {
                it.printStackTrace()
                onCancelled.invoke()
            })
    }

    private fun getString(@StringRes resId: Int) = fragmentActivity.getString(resId)

}