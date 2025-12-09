package com.sypark.openTicket

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.sypark.openTicket.base.BaseActivity
import com.sypark.openTicket.databinding.ActivityMainBinding
import com.sypark.openTicket.view.MainFragment
import com.sypark.openTicket.view.RecommendFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        val isLogin = true

        if (isLogin) {
            navGraph.setStartDestination(R.id.mainFragment)
        } else {
            navGraph.setStartDestination(R.id.recommendFragment)
        }

        binding.layoutSelector.radioGroupMain.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_home -> {
                    supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, MainFragment())
                        .commit()
                }
                R.id.radio_recommend -> {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, RecommendFragment())
                        .commit()
                }
            }
        }

        navController.graph = navGraph
    }

}