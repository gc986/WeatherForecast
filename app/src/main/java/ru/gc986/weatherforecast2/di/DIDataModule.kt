package ru.gc986.weatherforecast2.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gc986.simplenotebook.m.DataCenter
import ru.gc986.simplenotebook.m.DataCenterI
import javax.inject.Singleton

@Module
class DIDataModule(private var context: Context) {

    @Provides
    @Singleton
    fun provideRequests(): DataCenterI = DataCenter(context)

}