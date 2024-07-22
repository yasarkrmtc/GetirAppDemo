package com.getir.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.R
import com.getir.data.api.Product
import com.getir.databinding.ProductListingCardItemBinding

class ProductListingAdapter :
    ListAdapter<Product, ProductListingAdapter.ProductViewHolder>(ProductDiffCallback()) {

    private var buttonClick: (item: Product) -> Unit = { item -> }

    fun buttonClick(item: (Product) -> Unit) {
        buttonClick = item
    }

    private var itemClick: (item: Product) -> Unit = { item -> }

    fun itemClick(item: (Product) -> Unit) {
        itemClick = item
    }

    inner class ProductViewHolder(private val binding: ProductListingCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.priceText
            binding.productAttribute.text = product.attribute
            binding.stoke.text = product.totalOrder.toString()
            if (product.totalOrder == 0) {
                binding.minus.visibility = View.GONE
                binding.stoke.visibility = View.GONE
            }
            Glide.with(binding.productImage.context).load(product.thumbnailURL)
                .into(binding.productImage)

            updateButtonsVisibility(binding, product.totalOrder)

            binding.addButton.setOnClickListener {
                product.totalOrder += 1
                binding.stoke.text = product.totalOrder.toString()
                buttonClick.invoke(product)
                updateButtonsVisibility(binding, product.totalOrder)
            }
            binding.minus.setOnClickListener {
                product.totalOrder -= 1
                binding.stoke.text = product.totalOrder.toString()
                buttonClick.invoke(product)
                updateButtonsVisibility(binding, product.totalOrder)
            }

            binding.productImage.setOnClickListener {
                itemClick.invoke(product)
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
    }

    private fun updateButtonsVisibility(binding: ProductListingCardItemBinding, totalOrder: Int) {
        if (totalOrder == 0) {
            binding.minus.visibility = View.GONE
            binding.stoke.visibility = View.GONE
            binding.cardProductImage.strokeColor = binding.root.context.getColor(R.color.view_divider_color)
        } else {
            binding.minus.visibility = View.VISIBLE
            binding.stoke.visibility = View.VISIBLE
            binding.minus.setImageResource(
                if (totalOrder == 1) R.drawable.purple_trash_icon else R.drawable.minus_icon
            )
            binding.cardProductImage.strokeColor = binding.root.context.getColor(
                if (totalOrder >= 1) R.color.app_purple else R.color.view_divider_color
            )
        }
    }
}
