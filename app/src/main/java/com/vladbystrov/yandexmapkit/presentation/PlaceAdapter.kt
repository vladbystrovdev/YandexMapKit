package com.vladbystrov.yandexmapkit.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladbystrov.yandexmapkit.databinding.PlaceItemBinding
import com.vladbystrov.yandexmapkit.domain.Place

class PlaceAdapter(private val onItemClicked: (Place) -> Unit) : ListAdapter<Place, PlaceAdapter.PlaceViewHolder>(DiffCallback) {

    class PlaceViewHolder(private var binding: PlaceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(place: Place) {
                binding.apply {
                    title.text = place.name
                    description.text = place.description
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val viwHolder = PlaceViewHolder(
            PlaceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viwHolder.itemView.setOnClickListener {
            val position = viwHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viwHolder
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }
}