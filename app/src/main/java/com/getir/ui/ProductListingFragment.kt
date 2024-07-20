package com.getir.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.getir.BaseFragment
import com.getir.R
import com.getir.adapters.ProductListingAdapter
import com.getir.adapters.ProductListiningSuggestedAdapter
import com.getir.databinding.FragmentProductListingBinding
import com.getir.utils.CustomAdaptiveDecoration
import com.getir.viewModel.ProductListingViewModel
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
                findNavController().navigate(R.id.action_productListingFragment_to_productBasketFragment)
            }
            viewModel.getProduct()
            viewModel.getSuggestedProduct()
        }
        initListener()
    }
    private fun initListener(){
        verticalAdapter.onItemClick {
            viewModel.updateDataBase(it)

        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    if (viewState.errorMessage != null) {
                        // Handle error
                    } else {
                        viewState.isLoading?.let {
                            binding.homeProgressBar.isVisible = it
                        }
                        viewState.productItemList?.let { demoResponses ->
                            Log.e("qqqq",viewState.productItemList.toString())
                            val products = demoResponses.flatMap { it.products ?: emptyList() }
                            verticalAdapter.submitList(products)
                        }
                        viewState.suggestedProductItemList?.let { demoResponses ->
                            Log.e("qqqq",viewState.suggestedProductItemList.toString())

                            val suggestedProducts = demoResponses.flatMap { it.products ?: emptyList() }
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

        // Apply custom item decoration
        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(),
            spanCount = 3,
            spacingHorizontal = 16,
            spacingVertical = 16
        )
        binding.recyclerViewVertical.addItemDecoration(itemDecoration)
    }

    private fun setupHorizontalRecyclerView() {
        binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHorizontal.adapter = horizontalAdapter

        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(),
            spanCount = 1,
            spacingHorizontal = 16,
            spacingVertical = 0
        )
        binding.recyclerViewHorizontal.addItemDecoration(itemDecoration)
    }
}

