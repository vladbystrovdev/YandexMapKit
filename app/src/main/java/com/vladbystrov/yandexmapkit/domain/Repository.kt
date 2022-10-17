package com.vladbystrov.yandexmapkit.domain

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun saveData(place: Place)
    fun fetchData(): Flow<List<Place>>
    suspend fun deleteData(place: Place)
}