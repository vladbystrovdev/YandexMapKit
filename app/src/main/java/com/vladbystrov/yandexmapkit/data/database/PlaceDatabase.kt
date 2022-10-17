package com.vladbystrov.yandexmapkit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalPlace::class], version = 1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}