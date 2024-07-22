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
import com.getir.databinding.SuggesteProductListingCardItemBinding

class BasketSuggestedAdaper :
    ListAdapter<Product, BasketSuggestedAdaper.ProductViewHolder>(ProductDiffCallback()) {

    private var buttonClick: (item: Product) -> Unit =
        { item -> }

    fun buttonClick(item: (Product) -> Unit) {
        buttonClick = item
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



            binding.addButton.setOnClickListener {
                product.totalOrder = 1
                buttonClick.invoke(product)

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

}
