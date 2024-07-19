package com.getir.ui


import android.os.Bundle
import android.view.View
import com.getir.BaseFragment
import com.getir.databinding.FragmentProductBasketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductBasketFragment :
    BaseFragment<FragmentProductBasketBinding>(FragmentProductBasketBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

        }
    }
}
