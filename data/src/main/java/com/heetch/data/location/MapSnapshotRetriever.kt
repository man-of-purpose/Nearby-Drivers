package com.heetch.data.location

interface MapSnapshotRetriever {

    fun retrieveSnapshotUrl(latitude: Double, longitude: Double) : String

    fun generateSnapshotUrl(latitude: Double, longitude: Double): String

}