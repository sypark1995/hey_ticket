package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override fun init(view: View) {

        binding.imgClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.textSearch.setOnEditorActionListener { v, actionId, event ->
            TODO("Not yet implemented")

        }
    }
}