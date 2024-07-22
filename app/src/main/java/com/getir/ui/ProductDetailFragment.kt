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

            product.let {
                Glide.with(requireContext()).load(product.thumbnailURL).into(productImage)
                productName.text = it.name
                productPrice.text = it.priceText
                productAttribute.text = it.attribute
                when (it.totalOrder) {
                    0 -> chartAdd.visibility = View.VISIBLE
                    1 -> binding.minus.setImageResource(
                        R.drawable.purple_trash_icon
                    )
                    else -> {
                        cardAdd.visibility = View.VISIBLE
                        stoke.text = it.totalOrder.toString()
                    }
                }
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
                    item?.let {
                        binding.stoke.text = it.totalOrder.toString()
                        if (it.totalOrder == 0) findNavController().popBackStack() else {
                            binding.cardAdd.visibility = View.VISIBLE
                            binding.stoke.text = it.totalOrder.toString()
                        }
                        binding.minus.setImageResource(
                            if (it.totalOrder == 1) R.drawable.purple_trash_icon else R.drawable.minus_icon
                        )
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
