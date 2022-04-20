package com.heetch.domain.usecase.drivers

import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.domain.repository.drivers.DriversRepository
import com.heetch.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetNearbyDriversUseCase constructor(
    private val driversRepository: DriversRepository
) : SingleUseCase<List<DriverDomainModel>, GetNearbyDriversParams> {

    override fun invoke(params: GetNearbyDriversParams): Single<List<DriverDomainModel>> {
        return driversRepository.getNearbyDrivers(params.latitude, params.longitude)
            .subscribeOn(Schedulers.io())
            .retry() // retry all failed attempts since the behaviour requires drivers to be continuously streamed
    }
}

class GetNearbyDriversParams(
    val latitude: Double,
    val longitude: Double
)