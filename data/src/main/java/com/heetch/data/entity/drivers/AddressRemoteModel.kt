package com.heetch.data.entity.drivers

import com.heetch.domain.entity.drivers.AddressDomainModel

data class AddressRemoteModel(
    val address: String,
    val snapshotURL: String
) {
    fun toDomainModel(): AddressDomainModel = AddressDomainModel(address, snapshotURL)
}