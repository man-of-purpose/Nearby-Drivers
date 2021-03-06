package com.heetch.data.location

import android.location.Address
import io.reactivex.Observable

interface AddressRetriever {

    fun geocode(latitude: Double, longitude: Double): Observable<Address>

}