package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentSearchPerformanceBinding
import com.sypark.openTicket.model.SearchViewModel
import com.sypark.openTicket.view.SearchPerformanceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPerformanceFragment :
    BaseFragment<FragmentSearchPerformanceBinding>(R.layout.fragment_search_performance) {

    private val viewModel: SearchViewModel by viewModels({ requireActivity() })

    override fun init(view: View) {

        binding.recyclerviewPerformance.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = SearchPerformanceAdapter {
                itemClicked()
            }
        }
    }

    private fun itemClicked() {
//        findNavController().navigate()
    }
}