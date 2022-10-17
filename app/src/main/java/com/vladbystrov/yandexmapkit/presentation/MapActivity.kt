package com.vladbystrov.yandexmapkit.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.coroutineScope
import com.vladbystrov.yandexmapkit.R
import com.vladbystrov.yandexmapkit.databinding.ActivityMapBinding
import com.vladbystrov.yandexmapkit.domain.Place
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapActivity : AppCompatActivity(), InputListener {

    private lateinit var binding: ActivityMapBinding
    private lateinit var locationManager: LocationManager
    private val viewModel by viewModels<MapViewModel>()
    private lateinit var mapObjects: MapObjectCollection
    private val marks = mutableMapOf<Place, PlacemarkMapObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestLocationPermission()

        initMap()

        initUserLocationLayer()

        binding.fabShowPlace.setOnClickListener {
            PlacesBottomSheetDialogFragment(
                viewModel.loadPlaces()
            ) {
                moveCameraToPoint(binding.mapView, Point(it.latitude, it.longitude))
            }.show(supportFragmentManager, "bottom")
        }

        showMarkOnMap()
    }

    private fun initUserLocationLayer() {
        val mapKit: MapKit = MapKitFactory.getInstance()
        val locationMapKit = mapKit.createUserLocationLayer(binding.mapView.mapWindow)
        locationMapKit.isVisible = true
    }

    private fun initMap() {
        MapKitFactory.initialize(this)
        binding.mapView.map.addInputListener(this);
        locationManager = MapKitFactory.getInstance().createLocationManager()
        getCoordinationAndMove()
        binding.mapView.map.isFastTapEnabled
    }

    private fun showMarkOnMap() {
        mapObjects = binding.mapView.map.mapObjects.addCollection()
        lifecycle.coroutineScope.launch {
            viewModel.loadPlaces().collect() {
                it.forEach { place ->
                    val view = View(this@MapActivity).apply {
                        background = getDrawable(R.drawable.ic_place)
                    }
                    val mark = mapObjects.addPlacemark(
                        Point(place.latitude, place.longitude),
                        ViewProvider(view)
                    )
                    marks[place] = mark
                    mark.addTapListener { p0, p1 ->
                        showDialogInfoPlace(place)
                        true
                    }
                }
            }
        }
    }

    private fun showDialog(latitude: Double, longitude: Double) {
        NewPlaceDialogFragment(
            onAddClickListener = { name: String, description: String ->
                viewModel.savePlace(name, description, latitude, longitude)
            }
        ).show(supportFragmentManager, "dialog")
    }

    private fun showDialogInfoPlace(place: Place) {
        PlaceInfoDialogFragment(
            place,
            onDeleteClickListener = {
                viewModel.deletePlace(it)
                marks[place]?.let { it1 -> mapObjects.remove(it1) }
                marks.remove(place)
            }
        ).show(supportFragmentManager, "dialog")
    }


    private fun getCoordinationAndMove() {
        locationManager.requestSingleUpdate(object : com.yandex.mapkit.location.LocationListener {
            override fun onLocationUpdated(location: Location) {
                moveCameraToPoint(
                    binding.mapView,
                    Point(location.position.latitude, location.position.longitude)
                )
            }

            override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
                if (locationStatus == LocationStatus.AVAILABLE) {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun moveCameraToPoint(mapView: MapView, point: Point) {
        mapView.map.move(
            CameraPosition(point, 15.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2f),
            null
        )
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        binding.mapView.onStop()
    }

    override fun onMapTap(p0: Map, p1: Point) {

    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        showDialog(p1.latitude, p1.longitude)
    }
}