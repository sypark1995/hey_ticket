package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {
    override fun init(view: View) {

        binding.imgBack.setOnClickListener {
            findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToCategoryFragment())
        }
    }
}