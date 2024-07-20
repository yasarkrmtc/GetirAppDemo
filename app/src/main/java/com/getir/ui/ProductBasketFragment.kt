package com.getir.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.getir.BaseFragment
import com.getir.adapters.ChartAdapter
import com.getir.adapters.ProductListingAdapter
import com.getir.databinding.FragmentProductBasketBinding
import com.getir.utils.CustomAdaptiveDecoration
import com.getir.viewModel.ProductBasketViewModel
import com.getir.viewModel.ProductListingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductBasketFragment :
    BaseFragment<FragmentProductBasketBinding>(FragmentProductBasketBinding::inflate) {
    private val viewModel: ProductBasketViewModel by viewModels()

    private val adapter = ChartAdapter()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
        setupRecyclerView()
        observeData()
        viewModel.getLocalItems()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { items ->
                if (items.isNotEmpty()) {
                    adapter.submitList(items)
                }

            }
        }
    }
    private fun setupRecyclerView() {
        binding.recyclerViewVertical.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerViewVertical.adapter = adapter

        // Apply custom item decoration
        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(),
            spanCount = 1,
            spacingHorizontal = 0,
            spacingVertical = 16
        )
        binding.recyclerViewVertical.addItemDecoration(itemDecoration)
    }
}
