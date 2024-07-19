package com.getir.ui


import android.os.Bundle
import android.view.View
import com.getir.BaseFragment
import com.getir.databinding.FragmentProductDetailBinding


class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

        }
    }
}
