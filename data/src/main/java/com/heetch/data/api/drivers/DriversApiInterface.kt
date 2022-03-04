package com.heetch.data.api.drivers

import com.heetch.data.entity.drivers.CoordinatesBody
import com.heetch.data.entity.drivers.DriverRemoteModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.PUT

interface DriversApiInterface {

    @PUT("coordinates")
    fun getCoordinates(@Body coordinatesBody: CoordinatesBody): Single<List<DriverRemoteModel>>
}