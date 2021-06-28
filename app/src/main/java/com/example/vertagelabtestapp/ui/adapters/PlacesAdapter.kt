package com.example.vertagelabtestapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vertagelabtestapp.data.models.Place
import com.example.vertagelabtestapp.databinding.ItemPlaceBinding

class PlacesAdapter : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {
    var items: List<Place> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PlacesViewHolder(private var binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Place) {
            binding.tvId.setText("${item.id}.")
            binding.tvName.setText(item.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        return PlacesViewHolder(ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    override fun getItemCount(): Int = items.size
}