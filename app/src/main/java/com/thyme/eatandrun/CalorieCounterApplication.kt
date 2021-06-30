package com.thyme.eatandrun

import android.app.Application
import com.google.firebase.FirebaseApp
import timber.log.Timber

class CalorieCounterApplication : Application() {
    companion object {
        lateinit var instance: CalorieCounterApplication
    }


    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        instance = this
    }
}