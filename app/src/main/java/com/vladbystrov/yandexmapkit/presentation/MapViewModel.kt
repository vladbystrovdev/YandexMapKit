package com.vladbystrov.yandexmapkit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladbystrov.yandexmapkit.data.RepositoryImpl
import com.vladbystrov.yandexmapkit.domain.Place
import com.vladbystrov.yandexmapkit.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

    fun savePlace(name: String, description: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                repository.saveData(
                    Place(
                        name = name,
                        description = description,
                        latitude = latitude,
                        longitude = longitude
                    )
                )
            }
        }

    }

    fun loadPlaces(): Flow<List<Place>> = repository.fetchData()

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            repository.deleteData(place)
        }
    }

}