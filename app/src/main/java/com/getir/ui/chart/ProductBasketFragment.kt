package com.getir.ui.chart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.getir.base.BaseFragment
import com.getir.R
import com.getir.data.remote.Product
import com.getir.databinding.FragmentProductBasketBinding
import com.getir.utils.CustomAdaptiveDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductBasketFragment :
    BaseFragment<FragmentProductBasketBinding>(FragmentProductBasketBinding::inflate) {
    private val viewModel: ProductBasketViewModel by viewModels()

    private val adapter = ChartAdapter()
    private val horizontalAdapter = BasketSuggestedAdaper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHorizontalRecyclerView()
        binding.apply {
            customToolBar.navigationIconCloseSetOnClickListener {
                findNavController().popBackStack()
            }
            customToolBar.whiteTrashBtnSetOnClickListener {
                checkAndShowClearCartConfirmationDialog()
            }
            orderDoneButtonCard.setOnClickListener {
                checkAndShowOrderCompleteDialog()
            }
        }
        setupRecyclerView()
        observeData()
        viewModel.getLocalItems()
        viewModel.getTotalPrice()
        initListener()
    }

    private fun initListener() {
        horizontalAdapter.buttonClick {
            viewModel.updateDataBase(it)
        }
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
                    totalOrder = it.totalOrder,
                    squareThumbnailURL = it.squareThumbnailURL,
                    category = it.category,
                    status = it.status,
                    unitPrice = it.unitPrice
                )
            )
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { items ->
                if (items.isNotEmpty()) {
                    adapter.submitList(items)
                    viewModel.getProduct()
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.suggestedItemList.collect { products ->
                    if (!products.isNullOrEmpty()){
                        val suggestedProducts =
                            products.flatMap { it.products ?: emptyList() }
                        horizontalAdapter.submitList(suggestedProducts)
                    }else{
                        horizontalAdapter.submitList(listOf())

                    }

                }
            }
        }

    }

    private fun setupRecyclerView() {
        binding.recyclerViewVertical.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerViewVertical.adapter = adapter

        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(), spanCount = 1, spacingHorizontal = 0, spacingVertical = 0
        )
        binding.recyclerViewVertical.addItemDecoration(itemDecoration)
    }

    private fun checkAndShowClearCartConfirmationDialog() {
        viewLifecycleOwner.lifecycleScope.launch {
            val items = viewModel.items.value
            if (items.isEmpty()) {
                showToast(getString(R.string.no_items_to_delete))
            } else {
                showClearCartConfirmationDialog()
            }
        }
    }

    private fun checkAndShowOrderCompleteDialog() {
        viewLifecycleOwner.lifecycleScope.launch {
            val items = viewModel.items.value
            if (items.isEmpty()) {
                showToast(getString(R.string.add_items_first))
            } else {
                showOrderCompleteDialog()
            }
        }
    }

    private fun showClearCartConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.confirm_clear_cart))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                lifecycleScope.launch {
                    viewModel.clearDatabase()
                }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun showOrderCompleteDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_order_complete, null)
        val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val dialog = dialogBuilder.create()
        dialog.show()

        dialogView.postDelayed({
            dialog.dismiss()
            viewModel.clearDatabase()
            findNavController().navigate(R.id.action_productBasketFragment_to_productListingFragment)
        }, 3000)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupHorizontalRecyclerView() {
        binding.recyclerViewHorizontal.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewHorizontal.adapter = horizontalAdapter

        val itemDecoration = CustomAdaptiveDecoration(
            context = requireContext(), spanCount = 1, spacingHorizontal = 10, spacingVertical = 10
        )
        binding.recyclerViewHorizontal.addItemDecoration(itemDecoration)
    }
}