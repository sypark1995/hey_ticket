package com.sypark.openTicket.view

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentSearchPerformanceBinding
import com.sypark.openTicket.model.SearchViewModel
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

//        viewModel.radioState.observe(requireActivity()) {
//            Log.e("SearchPerformanceFragment", it)
//            if (it == "performance") {
//                findNavController().navigate(SearchPerformanceFragmentDirections.actionSearchPerformanceFragmentToSearchSportFragment())
//            }
//        }
    }

    private fun itemClicked() {
        Log.e("!!", "클릭")
    }
}