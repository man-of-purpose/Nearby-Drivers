package com.heetch.domain.entity.drivers

data class DriverDomainModel(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val image: String,
    val coordinates: CoordinatesDomainModel
)

data class CoordinatesDomainModel(val latitude: Double, val longitude: Double)