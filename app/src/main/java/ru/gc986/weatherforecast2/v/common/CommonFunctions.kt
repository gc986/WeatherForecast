package ru.gc986.weatherforecast2.v.common

import timber.log.Timber

inline fun Any?.log(prefix: String = "object:") = Timber.d("$prefix${toString()}")