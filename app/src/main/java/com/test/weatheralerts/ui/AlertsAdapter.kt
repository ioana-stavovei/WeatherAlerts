package com.test.weatheralerts.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.test.weatheralerts.R
import com.test.weatheralerts.databinding.AlertRowBinding
import com.test.weatheralerts.model.AlertProperties
import com.test.weatheralerts.utils.formatAndRemoveTimeDetailsFromStringUTC
import com.test.weatheralerts.utils.getDifferenceBetweenTwoStringDates

class AlertsAdapter :
    ListAdapter<AlertProperties, AlertsAdapter.AlertsViewHolder>(AlertsDiffCallback) {

    class AlertsViewHolder(private val binding: AlertRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alertProperties: AlertProperties) {
            loadImage(itemView.context, binding.alertIV, alertProperties.alertEntity.imageUrl)
            binding.alertNameTV.text = String.format(
                itemView.context.getString(R.string.name),
                alertProperties.alertEntity.name
            )
            binding.startDateTV.text = String.format(
                itemView.context.getString(R.string.start_date),
                alertProperties.alertEntity.startDate?.formatAndRemoveTimeDetailsFromStringUTC()
            )
            binding.endDateTV.text = String.format(
                itemView.context.getString(R.string.end_date),
                if (alertProperties.alertEntity.endDate.isNullOrEmpty()) itemView.context.getString(
                    R.string.empty_property
                ) else
                    alertProperties.alertEntity.endDate.formatAndRemoveTimeDetailsFromStringUTC()
            )
            binding.sourceTV.text = String.format(
                itemView.context.getString(R.string.source),
                alertProperties.alertEntity.sourceName
            )
            if (alertProperties.alertEntity.endDate.isNullOrEmpty()) {
                binding.endDateTV.text = String.format(
                    itemView.context.getString(R.string.end_date),
                    itemView.context.getString(R.string.empty_property)
                )
                binding.durationTV.text = String.format(
                    itemView.context.getString(R.string.duration),
                    itemView.context.getString(R.string.empty_property)
                )
            } else {
                binding.endDateTV.text = String.format(
                    itemView.context.getString(R.string.end_date),
                    alertProperties.alertEntity.endDate.formatAndRemoveTimeDetailsFromStringUTC()
                )
                binding.durationTV.text = String.format(
                    itemView.context.getString(R.string.duration),
                    alertProperties.alertEntity.startDate?.let {
                        getDifferenceBetweenTwoStringDates(
                            it,
                            alertProperties.alertEntity.endDate
                        )
                    }
                )
            }
        }

        private fun loadImage(context: Context, imageView: ImageView, url: String) {
            Picasso.with(context).invalidate("")
            Picasso.with(context).load(url)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .tag(context)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {
        val binding = AlertRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlertsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        val alertProperties = getItem(position)
        holder.bind(alertProperties)
    }
}

object AlertsDiffCallback : DiffUtil.ItemCallback<AlertProperties>() {
    override fun areItemsTheSame(oldItem: AlertProperties, newItem: AlertProperties): Boolean {
        return oldItem.alertEntity === newItem.alertEntity
    }

    override fun areContentsTheSame(oldItem: AlertProperties, newItem: AlertProperties): Boolean {
        return oldItem.alertEntity.name == newItem.alertEntity.name && oldItem.alertEntity.startDate == newItem.alertEntity.startDate && oldItem.alertEntity.endDate == newItem.alertEntity.endDate && oldItem.alertEntity.sourceName == newItem.alertEntity.sourceName && oldItem.alertEntity.imageUrl == newItem.alertEntity.imageUrl
    }
}