package com.ansters.app.gmshms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ansters.app.gmshms.data.LocationLogModel
import com.ansters.app.gmshms.databinding.ItemLocationLogBinding

class LocationLogAdapter : ListAdapter<LocationLogModel, LocationLogAdapter.LocationLogViewHolder>(
    LocationLogDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationLogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationLogBinding.inflate(inflater, parent, false)
        return LocationLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationLogViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class LocationLogViewHolder(private val binding: ItemLocationLogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocationLogModel) {
            binding.tvUpdatedAt.text = item.updatedAt
            binding.tvLocationLog.text = item.location
        }
    }

}

class LocationLogDiffUtil : DiffUtil.ItemCallback<LocationLogModel>() {
    override fun areItemsTheSame(oldItem: LocationLogModel, newItem: LocationLogModel): Boolean {
        return oldItem.updatedAt == newItem.updatedAt
    }

    override fun areContentsTheSame(oldItem: LocationLogModel, newItem: LocationLogModel): Boolean {
        return oldItem.updatedAt == newItem.updatedAt && oldItem.location == newItem.location
    }
}