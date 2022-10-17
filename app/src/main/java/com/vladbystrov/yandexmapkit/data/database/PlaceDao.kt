package com.vladbystrov.yandexmapkit.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM localplace")
    fun getAll(): Flow<List<LocalPlace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: LocalPlace)

    @Delete
    suspend fun deletePlace(place: LocalPlace)
}