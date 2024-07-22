package com.getir.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.getir.base.BaseFragment
import com.getir.R
import com.getir.databinding.FragmentProductListingBinding
import com.getir.utils.CustomAdaptiveDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListingFragment :
    BaseFragment<FragmentProductListingBinding>(FragmentProductListingBinding::inflate) {

    private val viewModel: ProductListingViewModel by viewModels()
    private val verticalAdapter = ProductListingAdapter()
    private val horizontalAdapter = ProductListiningSuggestedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupVerticalRecyclerView()
        setupHorizontalRecyclerView()
        binding.apply {
            customToolBar.chartIconSetOnClickListener {
                if (viewModel.localPrice.value != "₺0.00"){
                    findNavController().navigate(R.id.action_productListingFragment_to_productBasketFragment)
                }else{
                    Toast.makeText(context, getString(R.string.message_add_product_first), Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.getProduct()
            viewModel.getSuggestedProduct()
        }
        initListener()
    }

    private fun initListener() {
        horizontalAdapter.buttonClick {suggestedProduct ->
            viewModel.updateDataBase(suggestedProduct)
        }
        verticalAdapter.buttonClick { product ->
            viewModel.updateDataBase(product)
        }

        verticalAdapter.itemClick { product ->
            val action =
                ProductListingFragmentDirections.actionProductListingFragmentToProductDetailFragment(
                    product
                )
            findNavController().navigate(action)
        }

        horizontalAdapter.itemClick { product ->
            val action =
                ProductListingFragmentDirections.actionProductListingFragmentToProductDetailFragment(
                    product
                )
            findNavController().navigate(action)
        }

    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    if (viewState.errorMessage != null) {
                    } else {
                        viewState.isLoading?.let {
                            binding.homeProgressBar.isVisible = it
                        }
                        viewState.productItemList?.let { demoResponses ->
                            val products = demoResponses.flatMap { it.products ?: emptyList() }
                            verticalAdapter.submitList(products)
                        }
                        viewState.suggestedProductItemList?.let { demoResponses ->
                            val suggestedProducts =
                                demoResponses.flatMap { it.products ?: emptyList() }
                            horizontalAdapter.submitList(suggestedProducts)
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

    private fun setupVerticalRecyclerView() {
        binding.recyclerViewVertical.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewVertical.adapter = verticalAdapter

        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(), spanCount = 3, spacingHorizontal = 16, spacingVertical = 16
        )
        binding.recyclerViewVertical.addItemDecoration(itemDecoration)
    }

    private fun setupHorizontalRecyclerView() {
        binding.recyclerViewHorizontal.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHorizontal.adapter = horizontalAdapter

        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(), spanCount = 1, spacingHorizontal = 8, spacingVertical = 8 , orientation = LinearLayoutManager.HORIZONTAL
        )
        binding.recyclerViewHorizontal.addItemDecoration(itemDecoration)
    }
}