package com.heetch.domain.usecase.drivers

import com.heetch.domain.entity.drivers.AddressDomainModel
import com.heetch.domain.repository.drivers.AddressRepository
import com.heetch.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetAddressFromLocationUseCase constructor(
    private val addressRepository: AddressRepository
) : SingleUseCase<AddressDomainModel, GetAddressFromLocationParams> {

    override fun invoke(params: GetAddressFromLocationParams): Single<AddressDomainModel> {
        return addressRepository.getAddressFromLocation(params.latitude, params.longitude)
    }
}

class GetAddressFromLocationParams(
    val latitude: Double,
    val longitude: Double
)