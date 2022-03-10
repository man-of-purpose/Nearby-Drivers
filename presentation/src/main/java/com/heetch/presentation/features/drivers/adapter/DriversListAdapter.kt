package com.heetch.presentation.features.drivers.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.presentation.R
import com.heetch.presentation.databinding.DriverItemBinding
import com.heetch.presentation.util.loadImagefromUrl

class DriversListAdapter : RecyclerView.Adapter<DriversListAdapter.DriversListViewHolder>() {
    private var driverModels: List<DriverDomainModel> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateDriversList(driverModels: List<DriverDomainModel>) {
        this@DriversListAdapter.driverModels = driverModels
        notifyDataSetChanged()
    }

    inner class DriversListViewHolder(val driverItem: DriverItemBinding) : RecyclerView.ViewHolder(driverItem.root) {

        fun bind(driverModel: DriverDomainModel) = with(driverItem.root.context) {
            driverItem.driver = driverModel

            val driverImageURL = getString(R.string.driver_image_base_url) + driverModel.image
            driverItem.driverImage.loadImagefromUrl(driverImageURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversListViewHolder {
        return DriversListViewHolder(
            DriverItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DriversListViewHolder, position: Int) {
        holder.bind(driverModels[position])
    }

    override fun getItemCount(): Int = driverModels.size
}