package com.heetch.domain.usecase

import com.heetch.domain.TestConstants
import com.heetch.domain.entity.drivers.CoordinatesDomainModel
import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.domain.repository.drivers.DriversRepository
import com.heetch.domain.usecase.drivers.GetNearbyDriversParams
import com.heetch.domain.usecase.drivers.GetNearbyDriversUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class GetNearbyDriversUseCaseTest {

    private lateinit var getNearbyDriversUseCase: GetNearbyDriversUseCase
    private val driversRepository: DriversRepository = mockk()

    @Before
    fun init() {
        getNearbyDriversUseCase = GetNearbyDriversUseCase(driversRepository)
        prepareCases()
    }

    private fun prepareCases() {
        every {
            driversRepository.getNearbyDrivers(
                TestConstants.latitude,
                TestConstants.longitude
            )
        } returns (Single.just(listOf(sampleDriver(), sampleDriver())))
    }

    private fun sampleDriver(): DriverDomainModel {
        return DriverDomainModel(
            firstname = "Abdul",
            lastname = "Rahman",
            id = 1,
            image = "https://dummy/abdul.jpg",
            coordinates = CoordinatesDomainModel(
                latitude = TestConstants.latitude,
                longitude = TestConstants.longitude
            )
        )
    }

    @Test
    fun `invoke should return list of drivers`() {
        val request = getNearbyDriversUseCase.invoke(
            GetNearbyDriversParams(TestConstants.latitude, TestConstants.longitude)
        )
        request
            .test()
            .await()
            .assertValue(listOf(sampleDriver(), sampleDriver()))

       // assert(request.map { it == listOf(sampleDriver(), sampleDriver()) }.blockingGet())
    }
}