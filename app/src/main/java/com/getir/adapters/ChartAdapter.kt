package com.getir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.getir.data.repository.ItemEntity
import com.getir.databinding.ChartItemBinding

class ChartAdapter :
    ListAdapter<ItemEntity, ChartAdapter.ChartViewHolder>(ChartDiffCallback()) {

    private var onItemClick: (item: ItemEntity) -> Unit =
        { item -> }

    fun onItemClick(item: (ItemEntity) -> Unit) {
        onItemClick = item
    }

    inner class ChartViewHolder(private val binding: ChartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ItemEntity) {
            binding.productName.text = product.name
            binding.productPrice.text = product.priceText
            binding.productAttribute.text = product.attribute

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val binding = ChartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChartDiffCallback : DiffUtil.ItemCallback<ItemEntity>() {
        override fun areItemsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
            return oldItem == newItem
        }
    }

}