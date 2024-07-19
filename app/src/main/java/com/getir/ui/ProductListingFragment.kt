package com.getir.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.getir.BaseFragment
import com.getir.databinding.FragmentProductListingBinding
import com.getir.viewModel.ProductListingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListingFragment :
    BaseFragment<FragmentProductListingBinding>(FragmentProductListingBinding::inflate) {

        private val viewModel:ProductListingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel.getProduct()
        }
    }


}
