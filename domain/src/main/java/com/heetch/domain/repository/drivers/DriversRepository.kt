package com.heetch.domain.repository.drivers

import com.heetch.domain.entity.drivers.DriverDomainModel
import io.reactivex.Single

interface DriversRepository {
    fun getNearbyDrivers(latitude: Double, longitude: Double): Single<List<DriverDomainModel>>
}