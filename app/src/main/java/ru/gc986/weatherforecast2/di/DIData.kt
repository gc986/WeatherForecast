package ru.gc986.weatherforecast2.di

import dagger.Component
import ru.gc986.weatherforecast2.vm.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DIDataModule::class])
interface DIData {

    fun inject(viewModel: MainViewModel)

}