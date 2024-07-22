package com.getir.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.R
import com.getir.data.remote.Product
import com.getir.databinding.SuggesteProductListingCardItemBinding
import com.getir.utils.clickWithDebounce

class ProductListiningSuggestedAdapter :
    ListAdapter<Product, ProductListiningSuggestedAdapter.ProductViewHolder>(ProductDiffCallback()) {

    private var buttonClick: (item: Product) -> Unit =
        { item -> }

    fun buttonClick(item: (Product) -> Unit) {
        buttonClick = item
    }

    private var itemClick: (item: Product) -> Unit =
        { item -> }

    fun itemClick(item: (Product) -> Unit) {
        itemClick = item
    }

    inner class ProductViewHolder(private val binding: SuggesteProductListingCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.priceText
            binding.productAttribute.text = product.shortDescription
            binding.stoke.text = product.totalOrder.toString()
            if (product.totalOrder == 0) {
                binding.minus.visibility = View.GONE
                binding.stoke.visibility = View.GONE
            }
            product.imageURL?.let {
                Glide.with(binding.productImage.context).load(product.imageURL).into(binding.productImage)
            }
            product.squareThumbnailURL?.let {
                Glide.with(binding.productImage.context).load(product.squareThumbnailURL).into(binding.productImage)
            }

            updateButtonsVisibility(binding, product.totalOrder)


            binding.addButton.clickWithDebounce {
                product.totalOrder += 1
                binding.stoke.text = product.totalOrder.toString()
                buttonClick.invoke(product)
                updateButtonsVisibility(binding, product.totalOrder)
            }
            binding.minus.clickWithDebounce {
                product.totalOrder -= 1
                binding.stoke.text = product.totalOrder.toString()
                buttonClick.invoke(product)
                updateButtonsVisibility(binding, product.totalOrder)
            }

            binding.productImage.clickWithDebounce {
                itemClick.invoke(product)
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

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private fun updateButtonsVisibility(binding: SuggesteProductListingCardItemBinding, totalOrder: Int) {
        if (totalOrder == 0) {
            binding.minus.visibility = View.GONE
            binding.stoke.visibility = View.GONE
            binding.cardProductImage.strokeColor = binding.root.context.getColor(
                R.color.view_divider_color
            )
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
