package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecommendBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {

    override fun init(view: View) {

        binding.textHome.setOnClickListener {
            findNavController().navigate(RecommendFragmentDirections.actionRecommendFragmentToMainFragment())
        }

    }

}