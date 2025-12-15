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

    private val sortList = listOf(
        CategoryDetailSort("최신순"),
        CategoryDetailSort("예매순"),
        CategoryDetailSort("조회수순"),
        CategoryDetailSort("기대평순")
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
            adapter = CategorySortAdapter(sortList) { selectedItem: CategoryDetailSort ->
                listItemClicked(selectedItem)
            }
        }

        binding.layoutSort.btnConfirm.setOnClickListener {
            Log.e("!!!!","click")
            binding.textSort.text =
//            categoryDetailViewModel.sortType.observe(this) {
//                binding.textSort.text = it
//            }
        }
    }

    private fun listItemClicked(list: CategoryDetailSort) {
        categoryDetailViewModel.setSortType(list.sort)
        Log.e("!!!!", list.sort)
    }

}