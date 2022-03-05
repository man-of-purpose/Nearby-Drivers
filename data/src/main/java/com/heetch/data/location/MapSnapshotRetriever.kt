package com.heetch.data.location

import android.graphics.Bitmap
import io.reactivex.Observable

interface MapSnapshotRetriever {

    fun retrieveSnapshotUrl(latitude: Double, longitude: Double) : String

    fun generateSnapshotUrl(latitude: Double, longitude: Double): String

}