package com.heetch.presentation.features.drivers

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DriversListViewModel constructor(
    private val getNearbyDriversUseCase: GetNearbyDriversUseCase
): ViewModel() {
    private var mutableLoading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = mutableLoading

    fun getNearbyDrivers(myLocation: Location){

    }
}