package com.getir.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.getir.BaseFragment
import com.getir.adapters.ProductListingAdapter
import com.getir.data.api.Product
import com.getir.databinding.FragmentProductListingBinding
import com.getir.utils.CustomAdaptiveDecoration
import com.getir.viewModel.ProductListingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListingFragment :
    BaseFragment<FragmentProductListingBinding>(FragmentProductListingBinding::inflate) {

    private val viewModel: ProductListingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.apply {
            viewModel.getProduct()
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
                        viewState.itemList?.let { demoResponses ->
                            val products = demoResponses.flatMap { it.products ?: emptyList() }
                            setupRecyclerView(products)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(products: List<Product>) {
        val adapter = ProductListingAdapter(products)

        // Horizontal RecyclerView setup remains unchanged
        binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHorizontal.adapter = adapter

        binding.recyclerViewVertical.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewVertical.adapter = adapter

        // Apply custom item decoration
        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(),
            spanCount = 2,
            spacingHorizontal = 16,
            spacingVertical = 16
        )
        binding.recyclerViewVertical.addItemDecoration(itemDecoration)
    }

}
