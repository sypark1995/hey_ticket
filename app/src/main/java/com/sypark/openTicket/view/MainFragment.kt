package com.sypark.openTicket.view

import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun init() {
        binding.viewpager.offscreenPageLimit = 5

        binding.viewpager.apply {

        }
    }
}