package com.heetch.presentation.features.drivers

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heetch.presentation.R
import com.jakewharton.rxbinding3.view.clicks
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_drivers.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider

class DriversListActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "DriversListActivity"
    }

    private val driversListViewModel: DriversListViewModel by inject()
    private val permissions =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers)
        setSupportActionBar(drivers_toolbar)

        compositeDisposable.add(subscribeToFabClick())
        observeViewStates()
    }

    private fun observeViewStates() = with(driversListViewModel) {
        drivers.observe(this@DriversListActivity) { drivers ->

        }

        loading.observe(this@DriversListActivity) { isLoading ->

        }
    }

    private fun getNearbyDrivers(userLocation: Location) {
        driversListViewModel.getNearbyDrivers(userLocation)
    }

    private fun subscribeToFabClick(): Disposable {
        return drivers_fab.clicks()
            .doOnNext { Toast.makeText(this, "Play!", Toast.LENGTH_SHORT).show() }
            .flatMap {
                checkPermissions()
                    .flatMap { getUserLocation() }
                    .doOnNext {
                        getNearbyDrivers(it)
                    }
            }
            .subscribe()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun checkPermissions(): Observable<Boolean> {
        return RxPermissions(this).request(*permissions)
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation(): Observable<Location> {
        return ReactiveLocationProvider(this).lastKnownLocation
    }

}