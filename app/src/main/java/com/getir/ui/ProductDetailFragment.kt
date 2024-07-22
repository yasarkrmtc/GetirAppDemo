package com.getir.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.getir.BaseFragment
import com.getir.R
import com.getir.data.api.Product
import com.getir.databinding.FragmentProductDetailBinding
import com.getir.utils.clickWithDebounce
import com.getir.viewModel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()

    val args: ProductDetailFragmentArgs by navArgs()
    lateinit var product: Product
    override fun onResume() {
        super.onResume()
        viewModel.getProduct(product.id)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = args.product
        viewModel.getTotalPrice()
        binding.apply {
            customToolBar.chartIconSetOnClickListener {
                findNavController().navigate(R.id.action_productDetailFragment_to_productBasketFragment)
            }
            customToolBar.navigationIconCloseSetOnClickListener {
                findNavController().popBackStack()

            }

        }
        binding.chartAdd.clickWithDebounce {
            product.totalOrder += 1
            binding.chartAdd.visibility = View.GONE
            binding.cardAdd.visibility = View.VISIBLE
            viewModel.updateDataBase(product)

        }

        binding.addButton.clickWithDebounce {
            product.totalOrder += 1
            binding.chartAdd.visibility = View.GONE
            binding.cardAdd.visibility = View.VISIBLE
            viewModel.updateDataBase(product)
        }
        binding.minus.clickWithDebounce {
            product.totalOrder -= 1
            if (product.totalOrder == 0) {
                binding.chartAdd.visibility = View.VISIBLE
                binding.cardAdd.visibility = View.GONE
            }
            viewModel.updateDataBase(product)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.item.collect { item ->
                    if (item != null) {
                        item?.let {
                            product = Product(
                                it.id,
                                it.name,
                                it.attribute,
                                it.squareThumbnailURL,
                                it.thumbnailURL,
                                it.imageURL,
                                it.price,
                                it.priceText,
                                it.category,
                                it.unitPrice,
                                it.status,
                                it.shortDescription,
                                it.totalOrder
                            )
                            binding.stoke.text = it.totalOrder.toString()
                            if (it.totalOrder == 0) findNavController().popBackStack() else {
                                binding.cardAdd.visibility = View.VISIBLE
                                binding.stoke.text = it.totalOrder.toString()
                            }
                            binding.minus.setImageResource(
                                if (it.totalOrder == 1) R.drawable.purple_trash_icon else R.drawable.minus_icon
                            )

                            product.imageURL?.let {
                                Glide.with(binding.productImage.context).load(product.imageURL)
                                    .into(binding.productImage)
                            }
                            product.squareThumbnailURL?.let {
                                Glide.with(binding.productImage.context)
                                    .load(product.squareThumbnailURL).into(binding.productImage)
                            }
                            binding.apply {
                                productName.text = it.name
                                productPrice.text = it.priceText
                                productAttribute.text = it.attribute
                                when (it.totalOrder) {
                                    0 -> chartAdd.visibility = View.VISIBLE
                                    1 -> {
                                        chartAdd.visibility = View.GONE
                                        cardAdd.visibility = View.VISIBLE
                                        stoke.text = it.totalOrder.toString()
                                        binding.minus.setImageResource(
                                            R.drawable.purple_trash_icon
                                        )
                                    }

                                    else -> {
                                        chartAdd.visibility = View.GONE
                                        cardAdd.visibility = View.VISIBLE
                                        stoke.text = it.totalOrder.toString()
                                    }
                                }
                            }

                        }
                    } else if (item == null) {
                        product.let {
                            binding.apply {
                                productName.text = it.name
                                productPrice.text = it.priceText
                                productAttribute.text = it.attribute
                                product.imageURL?.let {
                                    Glide.with(binding.productImage.context).load(product.imageURL)
                                        .into(binding.productImage)
                                }
                                product.squareThumbnailURL?.let {
                                    Glide.with(binding.productImage.context)
                                        .load(product.squareThumbnailURL).into(binding.productImage)
                                }
                                it.totalOrder = 0
                                chartAdd.visibility = View.VISIBLE
                                cardAdd.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.localPrice.collect { price ->
                    binding.customToolBar.setPrice(price)
                }
            }
        }
    }
}
