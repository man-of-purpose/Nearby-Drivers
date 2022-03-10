package com.heetch.presentation.features.drivers.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heetch.domain.entity.drivers.AddressDomainModel
import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.domain.usecase.drivers.GetAddressFromLocationParams
import com.heetch.domain.usecase.drivers.GetAddressFromLocationUseCase
import com.heetch.domain.usecase.drivers.GetNearbyDriversParams
import com.heetch.domain.usecase.drivers.GetNearbyDriversUseCase
import com.heetch.presentation.util.NumberConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit

class DriversListViewModel constructor(
    private val getNearbyDriversUseCase: GetNearbyDriversUseCase,
    private val getAddressFromLocationUseCase: GetAddressFromLocationUseCase
) : ViewModel(), KoinComponent {

    private var mutableDrivers: MutableLiveData<List<DriverDomainModel>> = MutableLiveData()
    val drivers: LiveData<List<DriverDomainModel>> = mutableDrivers

    private var mutableIsLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = mutableIsLoading

    private var mutableIsStreamingDrivers: MutableLiveData<Boolean> = MutableLiveData()
    val isStreamingDrivers: LiveData<Boolean> = mutableIsStreamingDrivers

    private var mutableUserAddress: MutableLiveData<AddressDomainModel> = MutableLiveData()
    val userAddress: LiveData<AddressDomainModel> = mutableUserAddress

    private lateinit var nearbyDriversSubscription: Disposable
    private lateinit var userAddressSubscription: Disposable

    fun toggleNearbyDriversStream(userLocation: Location) {
        val isStreaming = isStreamingDrivers.value ?: false

        if (isStreaming) {
            stopStreamingNearbyDrivers()
        } else {
            startStreamingNearbyDrivers(userLocation)
        }
    }

    private fun startStreamingNearbyDrivers(userLocation: Location) {
        val driversSubscriber = Consumer<List<DriverDomainModel>> { drivers ->
            mutableDrivers.value = drivers
            mutableIsLoading.value = false
            mutableIsStreamingDrivers.value = true
        }

        nearbyDriversSubscription = getNearbyDriversUseCase(
            GetNearbyDriversParams(
                userLocation.latitude,
                userLocation.longitude
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableIsLoading.value = true }
            .doOnSuccess {
                mutableIsLoading.value = false
                resolveUserAddressFromGeoLocation(userLocation)
            }
            .doOnError { mutableIsLoading.value = false }
            .repeatWhen {
                it.delay(NumberConstants.FIVE, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe(driversSubscriber)

    }

    private fun resolveUserAddressFromGeoLocation(userLocation: Location) {
        userAddressSubscription = getAddressFromLocationUseCase.invoke(
            GetAddressFromLocationParams(
                userLocation.latitude,
                userLocation.longitude
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { address ->
                mutableUserAddress.value = address
            }
    }

    private fun stopStreamingNearbyDrivers() {
        mutableIsLoading.value = false
        mutableIsStreamingDrivers.value = false
        dispose()
    }

    fun dispose() {
        nearbyDriversSubscription.dispose()
        userAddressSubscription.dispose()
    }
}