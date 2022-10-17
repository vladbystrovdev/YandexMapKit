package com.vladbystrov.yandexmapkit.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalPlace(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)
