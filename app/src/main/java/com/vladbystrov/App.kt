package com.vladbystrov

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("c4cc2238-17fe-4e91-a523-08cb47121fc9")
    }
}