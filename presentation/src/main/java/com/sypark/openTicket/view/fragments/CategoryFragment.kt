package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryBinding
import com.sypark.openTicket.model.CategoryViewModel
import com.sypark.openTicket.view.adapter.CategoryGenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    private val viewModel: CategoryViewModel by viewModels()

    private lateinit var categoryGenreAdapter: CategoryGenreAdapter
    override fun init(view: View) {
        binding.layoutBottom.navigationBottom.menu.getItem(1).isChecked = true

        binding.recyclerviewPerformance.apply {
            layoutManager = LinearLayoutManager(view.context)
            categoryGenreAdapter = CategoryGenreAdapter {
                findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToCategoryDetailFragment(it))
            }
            adapter = categoryGenreAdapter
        }

        binding.textSearch.setOnClickListener {
            findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToSearchFragment())
        }

        lifecycleScope.launch {
            viewModel.getGenreCountList()
        }

        viewModel.genreCountList.observe(viewLifecycleOwner) {
            categoryGenreAdapter.submitList(it)
        }
    }
}