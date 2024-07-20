package com.getir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.data.api.SuggestedProduct
import com.getir.databinding.SuggesteProductListingCardItemBinding

class ProductListiningSuggestedAdapter :
    ListAdapter<SuggestedProduct, ProductListiningSuggestedAdapter.ProductViewHolder>(ProductDiffCallback()) {

    inner class ProductViewHolder(private val binding: SuggesteProductListingCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: SuggestedProduct) {
            binding.productName.text = product.name
            binding.productPrice.text = product.priceText
            binding.productAttribute.text = product.shortDescription
            product.imageURL?.let {
                Glide.with(binding.productImage.context).load(product.imageURL).into(binding.productImage)
            }
            product.squareThumbnailURL?.let {
                Glide.with(binding.productImage.context).load(product.squareThumbnailURL).into(binding.productImage)

            }

            // Set click listener for add button
            binding.addButton.setOnClickListener {
                // Handle add button click
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = SuggesteProductListingCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<SuggestedProduct>() {
        override fun areItemsTheSame(oldItem: SuggestedProduct, newItem: SuggestedProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SuggestedProduct, newItem: SuggestedProduct): Boolean {
            return oldItem == newItem
        }
    }
}
