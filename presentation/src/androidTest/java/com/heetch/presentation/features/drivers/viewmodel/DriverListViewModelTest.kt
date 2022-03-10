package com.heetch.presentation.features.drivers.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.heetch.presentation.TestConstants
import com.heetch.domain.entity.drivers.AddressDomainModel
import com.heetch.domain.entity.drivers.CoordinatesDomainModel
import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.domain.usecase.drivers.GetAddressFromLocationParams
import com.heetch.domain.usecase.drivers.GetAddressFromLocationUseCase
import com.heetch.domain.usecase.drivers.GetNearbyDriversParams
import com.heetch.domain.usecase.drivers.GetNearbyDriversUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DriverListViewModelTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var driversListViewModel: DriversListViewModel
    private val getNearbyDriversUseCase: GetNearbyDriversUseCase =
        mockk<GetNearbyDriversUseCase>(relaxed = true)
    private val getAddressFromLocationUseCase: GetAddressFromLocationUseCase =
        mockk<GetAddressFromLocationUseCase>(relaxed = true)
    private val driversListObserver: Observer<List<DriverDomainModel>> = mockk()

    @Before
    fun init() {
        driversListViewModel =
            DriversListViewModel(getNearbyDriversUseCase, getAddressFromLocationUseCase)
        driversListViewModel.drivers.observeForever(driversListObserver)
        prepareCases()
    }

    private fun prepareCases() {
        every {
            getNearbyDriversUseCase.invoke(
                GetNearbyDriversParams(
                    TestConstants.latitude,
                    TestConstants.longitude
                )
            )
        } returns (Single.just(listOf(sampleDriver(), sampleDriver())))

        every {
            getAddressFromLocationUseCase.invoke(
                GetAddressFromLocationParams(
                    TestConstants.latitude,
                    TestConstants.longitude
                )
            )
        } returns (Single.just(sampleAddress()))
    }

    private fun sampleAddress(): AddressDomainModel {
        return AddressDomainModel(TestConstants.addressLine, TestConstants.snapshotURL)
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
    fun toggleNearbyDriversShouldReturnAList() {
        driversListViewModel.toggleNearbyDriversStream(
            TestConstants.latitude,
            TestConstants.longitude
        )

        driversListViewModel.drivers.observeForever {
            assert(
                it == listOf(
                    sampleDriver(),
                    sampleDriver()
                )
            )
        }
    }
}