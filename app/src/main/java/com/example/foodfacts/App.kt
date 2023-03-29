package com.example.foodfacts

import android.app.Application
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
