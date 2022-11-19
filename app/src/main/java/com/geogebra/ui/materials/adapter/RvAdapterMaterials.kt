package com.geogebra.ui.materials.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geogebra.core.dto.Material
import com.geogebra.databinding.LayoutRowItemMaterialsBinding

class RvAdapterMaterials(
    val callBack : (Material) -> Unit
) :
    ListAdapter<Material, RvAdapterMaterials.Holder>(object : DiffUtil.ItemCallback<Material>() {
        override fun areItemsTheSame(oldItem: Material, newItem: Material): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Material, newItem: Material): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutRowItemMaterialsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(private val binding: LayoutRowItemMaterialsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Material) {
            binding.data = data
            binding.materialItemConstraint.setOnClickListener {
                callBack.invoke(data)
            }
        }
    }
}