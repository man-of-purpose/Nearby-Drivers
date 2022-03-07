package com.heetch.presentation.features.drivers

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.heetch.presentation.R
import com.jakewharton.rxbinding3.view.clicks
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_drivers.*
import org.koin.android.ext.android.inject
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider

class DriversListActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "DriversListActivity"
    }

    private val driversListViewModel: DriversListViewModel by inject()
    private lateinit var driverListAdapter: DriversListAdapter
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
        setupDriverListAdapter()

        compositeDisposable.add(subscribeToFabClick())
        observeViewStates()
    }

    private fun setupDriverListAdapter() {
        driverListAdapter = DriversListAdapter()
        driversRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DriversListActivity)
            adapter = driverListAdapter
        }
    }

    private fun observeViewStates() = with(driversListViewModel) {
        drivers.observe(this@DriversListActivity) { drivers ->
            Toast.makeText(this@DriversListActivity, "Play!", Toast.LENGTH_SHORT).show()
            driverListAdapter.updateDriversList(drivers)
        }

        loading.observe(this@DriversListActivity) { isLoading ->

        }
    }

    private fun subscribeToFabClick(): Disposable {
        return drivers_fab.clicks()
            .doOnNext { Toast.makeText(this, "Play!", Toast.LENGTH_SHORT).show() }
            .flatMap {
                checkPermissions()
                    .flatMap { getUserLocation() }
                    .doOnNext { userLocation ->
                        driversListViewModel.getNearbyDrivers(userLocation)
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