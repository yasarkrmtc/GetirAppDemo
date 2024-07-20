package com.getir.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.data.api.Product
import com.getir.databinding.ProductListingCardItemBinding

class ProductListingAdapter :
    ListAdapter<Product, ProductListingAdapter.ProductViewHolder>(ProductDiffCallback()) {

    private var onItemClick: (item: Product) -> Unit =
        { item -> }

    fun onItemClick(item: (Product) -> Unit) {
        onItemClick = item
    }

    inner class ProductViewHolder(private val binding: ProductListingCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.priceText
            binding.productAttribute.text = product.attribute
            Log.e("ProductImage", product.thumbnailURL.toString())
            Glide.with(binding.productImage.context).load(product.thumbnailURL).into(binding.productImage)

            // Set click listener for add button
            binding.addButton.setOnClickListener {
                product.totalOrder += 1
                binding.stoke.text = product.totalOrder.toString()
                onItemClick.invoke(product)
            }
            binding.minusButton.setOnClickListener {
                product.totalOrder -= 1
                binding.stoke.text = product.totalOrder.toString()
                onItemClick.invoke(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductListingCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
}}
