package com.sypark.openTicket.view.fragments

import android.util.Log
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
            binding.layoutSort.layoutDetailSort.visibility =
                View.VISIBLE   //todo_sypark viewmodel로 한번에 관리
        }

        binding.layoutSort.imgClose.setOnClickListener {
            binding.layoutSort.layoutDetailSort.visibility =
                View.GONE  //todo_sypark viewmodel로 한번에 관리

            Preferences.sortPosition = 1
        }

        binding.layoutSort.sortRecyclerview.apply {
            layoutManager = LinearLayoutManager(view.context)
            categorySortAdapter = CategorySortAdapter { position ->
                onItemClicked(position)
                Preferences.sortPosition = position
            }

            categorySortAdapter.submitList(sortList)
            adapter = categorySortAdapter
            categorySortAdapter.setSelectedPosition(1)
        }

        binding.layoutSort.btnConfirm.setOnClickListener {
            binding.layoutSort.layoutDetailSort.visibility =
                View.GONE  //todo_sypark viewmodel로 한번에 관리

            binding.textSort.text = sortList[Preferences.sortPosition].sort
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