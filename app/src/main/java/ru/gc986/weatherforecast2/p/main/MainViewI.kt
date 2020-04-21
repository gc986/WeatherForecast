package ru.gc986.weatherforecast2.p.main

import com.arellomobile.mvp.MvpView

interface MainViewI: MvpView {

    fun showMessage(message: String)

}