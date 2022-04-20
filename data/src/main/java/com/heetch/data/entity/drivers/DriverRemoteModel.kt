package com.heetch.data.entity.drivers

import com.heetch.domain.entity.drivers.CoordinatesDomainModel
import com.heetch.domain.entity.drivers.DriverDomainModel

data class DriverRemoteModel(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val image: String,
    val coordinates: Coordinates
) {
    fun toDomainModel(): DriverDomainModel =
        DriverDomainModel(id, firstname, lastname, image, coordinates.toDomainModel())
}

data class Coordinates(val latitude: Double, val longitude: Double) {
    fun toDomainModel(): CoordinatesDomainModel = CoordinatesDomainModel(latitude, longitude)
}