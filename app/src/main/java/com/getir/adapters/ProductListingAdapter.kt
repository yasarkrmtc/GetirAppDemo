package com.getir.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.R
import com.getir.data.api.Product
import com.getir.databinding.ProductListingCardItemBinding

class ProductListingAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductListingAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ProductListingCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.priceText
            binding.productAttribute.text = product.attribute

             Glide.with(binding.productImage.context).load(product.thumbnailURL).into(binding.productImage)

            // Set click listener for add button
            binding.addButton.setOnClickListener {
                // Handle add button click
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
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
