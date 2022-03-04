package com.heetch.data.repository.drivers

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.PUT

interface ApiInterface {

    @PUT("coordinates")
    fun getCoordinates(@Body coordinatesBody: CoordinatesBody): Single<List<DriverRemoteModel>>
}