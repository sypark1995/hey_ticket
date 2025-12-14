package com.sypark.openTicket.view.fragments

import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.data.db.entity.CategoryDetailSort
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryDetailBinding
import com.sypark.openTicket.model.CategoryDetailViewModel
import com.sypark.openTicket.view.CategorySortAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()

    val sortList = listOf<CategoryDetailSort>(
//        CategoryDetailSort(,"")
    )
    override fun init(view: View) {

        binding.imgBack.setOnClickListener {
            findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToCategoryFragment())
        }

        binding.sortLayout.setOnClickListener {
            Log.e("sortLayout", "클릭")
            categoryDetailViewModel.setIsOpen(true)
            Log.e("sortLayout", categoryDetailViewModel.isOpen.value.toString())
//            binding.layoutSort.layoutDetailSort.visibility = View.VISIBLE
        }

        binding.layoutSort.imgClose.setOnClickListener {
            Log.e("imgClose", "클릭")
            categoryDetailViewModel.setIsOpen(false)
            Log.e("imgClose", categoryDetailViewModel.isOpen.value.toString())
//            binding.layoutSort.layoutDetailSort.visibility = View.GONE
        }

        binding.layoutSort.sortRecyclerview.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = CategorySortAdapter()
        }
    }
}