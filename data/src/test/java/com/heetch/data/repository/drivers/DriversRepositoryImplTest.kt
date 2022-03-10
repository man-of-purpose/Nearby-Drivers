package com.heetch.data.repository.drivers

import com.heetch.domain.TestConstants
import com.heetch.data.api.drivers.DriversApiInterface
import com.heetch.data.entity.drivers.Coordinates
import com.heetch.data.entity.drivers.CoordinatesBody
import com.heetch.data.entity.drivers.DriverRemoteModel
import com.heetch.data.location.LocationManager
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class DriversRepositoryImplTest {

    private lateinit var driversRepositoryImpl: DriversRepositoryImpl
    private val driversApiInterface: DriversApiInterface = mockk()
    private val locationManager: LocationManager = mockk()

    @Before
    fun init() {
        driversRepositoryImpl = DriversRepositoryImpl(driversApiInterface, locationManager)
        prepareCases()
    }

    private fun prepareCases() {
        every {
            driversApiInterface.getDrivers(
                coordinatesBody = CoordinatesBody(
                    latitude = TestConstants.latitude,
                    longitude = TestConstants.longitude
                )
            )
        }.returns(Single.just(listOf(sampleDriver())))
    }

    private fun sampleDriver(): DriverRemoteModel {
        return DriverRemoteModel(
            firstname = "Abdul",
            lastname = "Rahman",
            id = 1,
            image = "https://dummy/abdul.jpg",
            coordinates = Coordinates(
                latitude = TestConstants.latitude,
                longitude = TestConstants.longitude
            )
        )
    }

    @Test
    fun `getNearbyDrivers should return a list of drivers`() {
        val request =
            driversRepositoryImpl.getNearbyDrivers(TestConstants.latitude, TestConstants.longitude)

        assert(
            request.map {
                it == listOf(sampleDriver().toDomainModel())
            }.blockingGet()
        )
    }
}