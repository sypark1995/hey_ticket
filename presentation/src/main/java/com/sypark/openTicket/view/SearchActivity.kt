package com.sypark.openTicket.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseActivity
import com.sypark.openTicket.databinding.ActivitySearchBinding
import com.sypark.openTicket.model.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_search_fragment) as NavHostFragment

        navController = navHostFragment.navController

        binding.radioGroupSearch.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.radio_performance -> {
                    navController.navigate(SearchSportFragmentDirections.actionSearchSportFragmentToSearchPerformanceFragment())
                }

                R.id.radio_exhibition -> {
//                    viewModel.getRadioState("exhibition")
                }

                R.id.radio_sport -> {
                    navController.navigate(SearchPerformanceFragmentDirections.actionSearchPerformanceFragmentToSearchSportFragment())
                }
            }
        }
    }
}