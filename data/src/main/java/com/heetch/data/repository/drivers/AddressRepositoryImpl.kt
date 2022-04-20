package com.heetch.data.repository.drivers

import com.heetch.data.location.LocationManager
import com.heetch.domain.entity.drivers.AddressDomainModel
import com.heetch.domain.repository.drivers.AddressRepository
import io.reactivex.Single

class AddressRepositoryImpl constructor(
    private val locationManager: LocationManager
) : AddressRepository {
    override fun getAddressFromLocation(latitude: Double, longitude: Double): Single<AddressDomainModel> {
        val observableAddress = locationManager.getAddressFromCoordinates(latitude, longitude)
            .map { it.toDomainModel() }

        return Single.fromObservable(observableAddress)

    }
}