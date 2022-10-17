package com.vladbystrov.yandexmapkit.data

import com.vladbystrov.yandexmapkit.data.database.PlaceDao
import com.vladbystrov.yandexmapkit.domain.Place
import com.vladbystrov.yandexmapkit.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val placeDao: PlaceDao) : Repository {
    override suspend fun saveData(place: Place) {
        placeDao.insertPlace(place.map())
    }

    override fun fetchData(): Flow<List<Place>> = placeDao.getAll().map {
        it.map {
            Place(
                id = it.id,
                name = it.name,
                description = it.description,
                longitude = it.longitude,
                latitude = it.latitude
            )
        }
    }


    override suspend fun deleteData(place: Place) {
        placeDao.deletePlace(place.map())
    }
}