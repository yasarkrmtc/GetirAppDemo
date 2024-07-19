package com.getir.ui


import android.os.Bundle
import android.view.View
import com.getir.BaseFragment
import com.getir.databinding.FragmentProductListingBinding


class ProductListingFragment :
    BaseFragment<FragmentProductListingBinding>(FragmentProductListingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

        }
    }
}
