package com.getir.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.getir.BaseFragment
import com.getir.R
import com.getir.adapters.ChartAdapter
import com.getir.data.api.Product
import com.getir.databinding.FragmentProductBasketBinding
import com.getir.utils.CustomAdaptiveDecoration
import com.getir.viewModel.ProductBasketViewModel
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
        viewModel.getTotalPrice()
        initListener()
    }

    private fun initListener() {
        adapter.onItemClick {
            viewModel.updateDataBase(
                Product(
                    id = it.id,
                    name = it.name,
                    attribute = it.attribute,
                    thumbnailURL = it.thumbnailURL,
                    imageURL = it.imageURL,
                    price = it.price,
                    priceText = it.priceText,
                    shortDescription = it.shortDescription,
                    totalOrder = it.totalOrder
                )
            )

        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { items ->
                if (items.isNotEmpty()) {
                    adapter.submitList(items)
                } else {
                    adapter.submitList(listOf())
                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.localPrice.collect { price ->
                    binding.tvTotalPrice.text = price
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewVertical.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerViewVertical.adapter = adapter

        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(), spanCount = 1, spacingHorizontal = 0, spacingVertical = 16
        )
        binding.recyclerViewVertical.addItemDecoration(itemDecoration)
    }
}
