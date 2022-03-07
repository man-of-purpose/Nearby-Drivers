package com.heetch.domain.repository.drivers

import com.heetch.domain.entity.drivers.AddressDomainModel
import io.reactivex.Single

interface AddressRepository {
    fun getAddressFromLocation(latitude: Double, longitude: Double): Single<AddressDomainModel>
}