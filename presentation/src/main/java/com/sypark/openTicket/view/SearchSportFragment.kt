package com.sypark.openTicket.view

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentSearchSportBinding
import com.sypark.openTicket.model.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSportFragment :
    BaseFragment<FragmentSearchSportBinding>(R.layout.fragment_search_sport) {

    private val viewModel: SearchViewModel by viewModels({ requireActivity() })

    override fun init(view: View) {
        binding.recyclerviewSport.apply {
            setBackgroundColor(Color.BLACK)
        }

//        viewModel.radioState.observe(requireActivity()) {
//            Log.e("SearchSportFragment", it)
//            if (it == "sport") {
//                findNavController().navigate(SearchSportFragmentDirections.actionSearchSportFragmentToSearchPerformanceFragment())
//            }
//        }
    }

}