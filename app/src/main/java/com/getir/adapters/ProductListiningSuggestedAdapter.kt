package com.getir.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.R
import com.getir.data.api.SuggestedProduct
import com.getir.databinding.SuggesteProductListingCardItemBinding

class ProductListiningSuggestedAdapter :
    ListAdapter<SuggestedProduct, ProductListiningSuggestedAdapter.ProductViewHolder>(ProductDiffCallback()) {

    private var buttonClick: (item: SuggestedProduct) -> Unit =
        { item -> }

    fun buttonClick(item: (SuggestedProduct) -> Unit) {
        buttonClick = item
    }

    private var itemClick: (item: SuggestedProduct) -> Unit =
        { item -> }

    fun itemClick(item: (SuggestedProduct) -> Unit) {
        itemClick = item
    }

    inner class ProductViewHolder(private val binding: SuggesteProductListingCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: SuggestedProduct) {
            binding.productName.text = product.name
            binding.productPrice.text = product.priceText
            binding.productAttribute.text = product.shortDescription
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

    private fun updateButtonsVisibility(binding: SuggesteProductListingCardItemBinding, totalOrder: Int) {
        if (totalOrder == 0) {
            binding.minus.visibility = View.GONE
            binding.stoke.visibility = View.GONE
        } else {
            binding.minus.visibility = View.VISIBLE
            binding.stoke.visibility = View.VISIBLE
            binding.minus.setImageResource(
                if (totalOrder == 1) R.drawable.purple_trash_icon else R.drawable.minus_icon
            )
        }
    }
}
