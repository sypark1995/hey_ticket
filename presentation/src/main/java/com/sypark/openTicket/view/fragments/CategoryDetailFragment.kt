package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.data.db.entity.CategoryDetailSort
import com.sypark.openTicket.Preferences
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
    private lateinit var categorySortAdapter: CategorySortAdapter
    private lateinit var categoryFilterAreaAdapter: CategoryFilterAreaAdapter

    private val sortList = listOf(
        CategoryDetailSort("최근 등록순"),
        CategoryDetailSort("예매순"),
        CategoryDetailSort("조회수순")
    )

    override fun init(view: View) {

        binding.imgBack.setOnClickListener {
            findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToCategoryFragment())
            Preferences.sortPosition = 1
        }

        binding.sortLayout.setOnClickListener {
            binding.includeLayoutSort.layoutDetailSort.visibility =
                View.VISIBLE   //todo_sypark viewmodel로 한번에 관리
        }

        binding.includeLayoutSort.imgClose.setOnClickListener {
            binding.includeLayoutSort.layoutDetailSort.visibility =
                View.GONE  //todo_sypark viewmodel로 한번에 관리

            Preferences.sortPosition = 1
        }

        binding.includeLayoutSort.sortRecyclerview.apply {
            layoutManager = LinearLayoutManager(view.context)
            categorySortAdapter = CategorySortAdapter { position ->
                onItemClicked(position)
                Preferences.sortPosition = position
            }

            categorySortAdapter.submitList(sortList)
            adapter = categorySortAdapter
            categorySortAdapter.setSelectedPosition(1)
        }

        binding.includeLayoutSort.btnConfirm.setOnClickListener {
            binding.includeLayoutSort.layoutDetailSort.visibility =
                View.GONE  //todo_sypark viewmodel로 한번에 관리

            binding.textSort.text = sortList[Preferences.sortPosition].sort
        }

        binding.imgFilter.setOnClickListener {
            binding.includeLayoutFilter.root.visibility = View.VISIBLE
        }

        binding.includeLayoutFilter.imgClose.setOnClickListener {
            binding.includeLayoutFilter.root.visibility = View.GONE
        }

        binding.includeLayoutFilter.recyclerviewArea.apply {
            layoutManager = LinearLayoutManager(view.context)
            categoryFilterAreaAdapter = CategoryFilterAreaAdapter()
            adapter = categoryFilterAreaAdapter
            val itemList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
            categoryFilterAreaAdapter.submitList(itemList)
        }
    }

    private fun onItemClicked(position: Int) {
        categorySortAdapter.setSelectedPosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Preferences.sortPosition = 1
    }

}