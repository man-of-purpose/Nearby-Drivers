package com.heetch.data.repository.drivers

import com.heetch.data.api.drivers.DriversApiInterface
import com.heetch.data.entity.drivers.CoordinatesBody
import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.domain.repository.drivers.DriversRepository
import io.reactivex.Single

class DriversRepositoryImpl constructor(
    private val driversApiInterface: DriversApiInterface
) : DriversRepository {
    override fun getNearbyDrivers(
        latitude: Double,
        longitude: Double
    ): Single<List<DriverDomainModel>> {
        val request = driversApiInterface.getDrivers(
            coordinatesBody = CoordinatesBody(
                latitude = latitude,
                longitude = longitude
            )
        )
        return request.map { driverRemoteModelsList ->
            driverRemoteModelsList.map { driverRemoteModel ->
                driverRemoteModel.toDomainModel()
            }
        }
    }
}