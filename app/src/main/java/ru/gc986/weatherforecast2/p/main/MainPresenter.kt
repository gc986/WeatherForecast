package ru.gc986.weatherforecast2.p.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class MainPresenter: MvpPresenter<MainViewI>() {

    fun textProcess(text: String){
        viewState.showMessage(text + "000")
    }

}