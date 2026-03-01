package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryBinding
import com.sypark.openTicket.view.CategoryGenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private lateinit var categoryGenreAdapter: CategoryGenreAdapter
    override fun init(view: View) {
        binding.layoutBottom.navigationBottom.menu.getItem(1).isChecked = true

        binding.recyclerviewPerformance.apply {
            layoutManager = LinearLayoutManager(view.context)
            categoryGenreAdapter = CategoryGenreAdapter {
                itemClicked()
            }

            categoryGenreAdapter.submitList(Common.categoryList)
            adapter = categoryGenreAdapter
        }

        binding.textSearch.setOnClickListener {
            findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToSearchFragment())
        }
    }

    private fun itemClicked() {
        findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToCategoryDetailFragment())
    }

}