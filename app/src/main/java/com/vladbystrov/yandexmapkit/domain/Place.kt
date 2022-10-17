package com.vladbystrov.yandexmapkit.domain

import com.vladbystrov.yandexmapkit.data.database.LocalPlace
import java.util.*

data class Place(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
) {
    fun map(): LocalPlace = LocalPlace(
        id = id,
        name = name,
        description = description,
        latitude = latitude,
        longitude = longitude
    )
}
