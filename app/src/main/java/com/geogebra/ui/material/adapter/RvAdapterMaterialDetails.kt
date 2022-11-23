package com.geogebra.ui.material.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geogebra.core.dto.KeyValue
import com.geogebra.databinding.LayoutItemMaterialDetailsBinding

class RvAdapterMaterialDetails : ListAdapter<KeyValue, RvAdapterMaterialDetails.Holder>(object : DiffUtil.ItemCallback<KeyValue>(){
    override fun areItemsTheSame(oldItem: KeyValue, newItem: KeyValue): Boolean = oldItem.header == newItem.header

    override fun areContentsTheSame(oldItem: KeyValue, newItem: KeyValue): Boolean = oldItem.text == newItem.text
}) {

    inner class Holder(private val binding: LayoutItemMaterialDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : KeyValue) {
            binding.data = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutItemMaterialDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}