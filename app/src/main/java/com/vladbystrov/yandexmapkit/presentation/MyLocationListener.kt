package com.vladbystrov.yandexmapkit.presentation

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

class MyLocationListener: LocationListener {
    override fun onLocationUpdated(location: Location) {

    }

    override fun onLocationStatusUpdated(p0: LocationStatus) {
        TODO("Not yet implemented")
    }
}