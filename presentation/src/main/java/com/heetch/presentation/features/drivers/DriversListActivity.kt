package com.heetch.presentation.features.drivers

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.heetch.presentation.databinding.ActivityDriversBinding
import com.heetch.presentation.util.loadImagefromUrl
import com.jakewharton.rxbinding3.view.clicks
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_drivers.*
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
        setSupportActionBar(drivers_toolbar)
        setupDriverListAdapter()
        disposable.add(subscribeToFabClick())
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

    private fun subscribeToFabClick(): Disposable {
        return binding.driversFab.clicks()
            .subscribe {
                disposable.add(checkPermissionsThenStreamNearbyDrivers())
            }
    }

    private fun checkPermissionsThenStreamNearbyDrivers(): Disposable {
        return checkPermissions()
            .flatMap { getUserLocation() }
            .subscribe { userLocation ->
                driversListViewModel.toggleNearbyDriversStream(userLocation)
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