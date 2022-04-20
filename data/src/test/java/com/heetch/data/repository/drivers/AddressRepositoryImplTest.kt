package com.heetch.data.repository.drivers

import com.heetch.domain.TestConstants
import com.heetch.data.entity.drivers.AddressRemoteModel
import com.heetch.data.location.LocationManager
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class AddressRepositoryImplTest {

    private lateinit var addressRepositoryImpl: AddressRepositoryImpl
    private val locationManager: LocationManager = mockk()

    @Before
    fun init() {
        addressRepositoryImpl = AddressRepositoryImpl(locationManager)
        prepareCases()
    }

    private fun prepareCases() {
        every {
            locationManager.getAddressFromCoordinates(
                TestConstants.latitude,
                TestConstants.longitude
            )
        } returns (Observable.just(sampleAddress()))
    }

    private fun sampleAddress(): AddressRemoteModel {
        return AddressRemoteModel(TestConstants.addressLine, TestConstants.snapshotURL)
    }

    @Test
    fun `getAddressFromLocation should return single address`() {
        val request = addressRepositoryImpl.getAddressFromLocation(
            TestConstants.latitude,
            TestConstants.longitude
        )

        request
            .test()
            .await()
            .assertValue(sampleAddress().toDomainModel())
    }
}