package ru.gc986.weatherforecast2.v.common

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.content.pm.PackageInfoCompat
import com.google.android.gms.common.GoogleApiAvailability
import ru.gc986.weatherforecast.R


class CheckedPlayServices(val context: Context) {

    private val MIN_VER_OF_G_PLAY_SERVICES = 1104L

    fun toCheckPlayServices(
        onCompleted: () -> Unit,
        onFailed: (message: String) -> Unit
    ) {
        try {
            val v = PackageInfoCompat.getLongVersionCode(
                context.packageManager.getPackageInfo(
                    GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE,
                    0
                )
            ).toString().substring(0, 4).toLong()
            if (v < MIN_VER_OF_G_PLAY_SERVICES) onFailed(getString(R.string.app_can_t_continue_working))
            else onCompleted()
        } catch (e: Exception) {
            e.printStackTrace()
            onFailed(getString(R.string.you_dont_have_google_play_services_installed))
        }
    }

    private fun getString(@StringRes resId: Int): String = context.getString(resId)

}