package com.heetch.presentation.features.drivers.activity

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heetch.presentation.databinding.ActivityDriversBinding
import com.heetch.presentation.features.drivers.adapter.DriversListAdapter
import com.heetch.presentation.features.drivers.viewmodel.DriversListViewModel
import com.heetch.presentation.util.loadImagefromUrl
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider

class DriversListActivity : AppCompatActivity() {

    private val driversListViewModel: DriversListViewModel by inject()
    private lateinit var driverListAdapter: DriversListAdapter
    private lateinit var binding: ActivityDriversBinding
    private val permissions =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDriversBinding.inflate(LayoutInflater.from(this@DriversListActivity))
        setupUI()
        observeViewStates()
    }

    private fun setupUI() {
        setContentView(binding.root)
        setupDriverListAdapter()
        disposable.add(subscribeToFabClick())
        disposable.add(subscribeToPremiumSwitch())
    }

    private fun setupDriverListAdapter() {
        driverListAdapter = DriversListAdapter()
        binding.driversRecyclerView.adapter = driverListAdapter
    }

    private fun observeViewStates() = with(driversListViewModel) {
        drivers.observe(this@DriversListActivity) { drivers ->
            driverListAdapter.updateDriversList(drivers)
        }

        isLoading.observe(this@DriversListActivity) { isLoading ->
            binding.isLoading = isLoading
        }

        isStreamingDrivers.observe(this@DriversListActivity) { isStreamingDrivers ->
            binding.isStreamingDrivers = isStreamingDrivers
        }

        userAddress.observe(this@DriversListActivity) { userAddress ->
            binding.userAddress = userAddress
            binding.addressSnapshot.loadImagefromUrl(userAddress.snapshotURL)
        }
    }

    private fun subscribeToPremiumSwitch(): Disposable = with(binding) {
        return premiumSwitch.checkedChanges()
            .subscribe {
                isNotPremium = !isNotPremium
            }
    }

    private fun subscribeToFabClick(): Disposable {
        return binding.driversFab.clicks()
            .subscribe {
                disposable.add(checkPermissionsThenGetUserLocation())
            }
    }

    private fun checkPermissionsThenGetUserLocation(): Disposable {
        return checkPermissions()
            .map { isPermissionsAccepted ->
                if (isPermissionsAccepted) {
                    getUserLocationThenStreamNearbyDrivers()
                } else {
                    toastPermissionError()
                    Observable.empty<Any>()
                }
            }.subscribe()

    }

    private fun toastPermissionError() {
        //Note: String below will be fetched from String resources. Not hardcoded as below
        Toast.makeText(
            this@DriversListActivity,
            "You denied location permissions which are required to use NearbyDrivers app",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getUserLocationThenStreamNearbyDrivers(): Disposable {
        return getUserLocation()
            .subscribe { userLocation ->
                driversListViewModel.toggleNearbyDriversStream(
                    userLocation.latitude,
                    userLocation.longitude
                )
            }
    }

    private fun checkPermissions(): Observable<Boolean> {
        return RxPermissions(this).request(*permissions)
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation(): Observable<Location> {
        return ReactiveLocationProvider(this).lastKnownLocation
    }

    override fun onDestroy() {
        disposable.dispose()
        driversListViewModel.dispose()
        super.onDestroy()
    }
}