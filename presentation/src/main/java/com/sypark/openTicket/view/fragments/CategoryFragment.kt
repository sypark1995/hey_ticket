package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryBinding
import com.sypark.openTicket.view.SearchPerformanceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    override fun init(view: View) {
        binding.layoutBottom.navigationBottom.menu.getItem(1).isChecked = true

        binding.recyclerviewPerformance.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = SearchPerformanceAdapter {
                itemClicked()
            }
        }

        binding.textSearch.setOnClickListener {
            findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToSearchFragment())
        }
    }

    private fun itemClicked() {
        findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToCategoryDetailFragment())
    }

}