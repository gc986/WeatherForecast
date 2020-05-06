package ru.gc986.weatherforecast2.v.common

import android.app.Activity
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationManager{

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val location = MutableLiveData<Location>()

    fun getLocation(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                location.value = it
            }
    }

}