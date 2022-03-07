package com.heetch.presentation.features.drivers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heetch.domain.entity.drivers.DriverDomainModel
import com.heetch.presentation.databinding.DriverItemBinding
import com.heetch.presentation.util.RxPicasso

class DriversListAdapter : RecyclerView.Adapter<DriversListAdapter.DriversListViewHolder>() {
    private var driverModels: List<DriverDomainModel> = listOf()

    fun updateDriversList(driverModels: List<DriverDomainModel>) {
        this@DriversListAdapter.driverModels = driverModels
    }

    inner class DriversListViewHolder(val driverItem: DriverItemBinding) : RecyclerView.ViewHolder(driverItem.root) {
        fun bind(driverModel: DriverDomainModel) {
            driverItem.driver = driverModel
            RxPicasso().loadImage(driverModel.image)
                .subscribe { driverItem.driverImage.setImageBitmap(it) }.dispose()
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