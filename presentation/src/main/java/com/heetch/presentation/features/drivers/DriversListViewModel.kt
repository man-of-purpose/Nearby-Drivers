package com.heetch.presentation.features.drivers

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.domain.usecase.drivers.GetNearbyDriversParams
import com.heetch.domain.usecase.drivers.GetNearbyDriversUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent

class DriversListViewModel constructor(
    private val getNearbyDriversUseCase: GetNearbyDriversUseCase
) : ViewModel(), KoinComponent {

    private val compositeDisposable = CompositeDisposable()

    private var mutableDrivers: MutableLiveData<List<DriverDomainModel>> = MutableLiveData()
    val drivers: LiveData<List<DriverDomainModel>> = mutableDrivers
    private var mutableLoading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = mutableLoading

    fun getNearbyDrivers(myLocation: Location) {
        compositeDisposable.add(
            getNearbyDriversUseCase(
            GetNearbyDriversParams(
                myLocation.latitude,
                myLocation.longitude
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableLoading.value = true }
            .doOnSuccess { mutableLoading.value = false }
            .subscribe({ drivers ->
                mutableDrivers.value = drivers
                mutableLoading.value = false
            }, {})
        )
    }
}