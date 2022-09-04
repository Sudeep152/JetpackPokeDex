package com.shashank.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber

@HiltAndroidApp
class PokeDexApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}