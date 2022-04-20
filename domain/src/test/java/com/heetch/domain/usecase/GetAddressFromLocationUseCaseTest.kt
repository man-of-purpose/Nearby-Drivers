package com.heetch.domain.usecase

import com.heetch.domain.TestConstants
import com.heetch.domain.entity.drivers.AddressDomainModel
import com.heetch.domain.repository.drivers.AddressRepository
import com.heetch.domain.usecase.drivers.GetAddressFromLocationParams
import com.heetch.domain.usecase.drivers.GetAddressFromLocationUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetAddressFromLocationUseCaseTest {

    private lateinit var getAddressFromLocationUseCase: GetAddressFromLocationUseCase
    private val addressRepository: AddressRepository = mockk()

    @Before
    fun init() {
        getAddressFromLocationUseCase = GetAddressFromLocationUseCase((addressRepository))
        prepareCases()
    }

    private fun prepareCases() {
        every {
            addressRepository.getAddressFromLocation(
                TestConstants.latitude,
                TestConstants.longitude
            )
        } returns (Single.just(sampleAddress()))
    }

    private fun sampleAddress(): AddressDomainModel {
        return AddressDomainModel(TestConstants.addressLine, TestConstants.snapshotURL)
    }

    @Test
    fun `invoke should return address`() {
        val request = getAddressFromLocationUseCase.invoke(
            GetAddressFromLocationParams(TestConstants.latitude, TestConstants.longitude)
        )

        request
            .test()
            .await()
            .assertValue(sampleAddress())
    }
}