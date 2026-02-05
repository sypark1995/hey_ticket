package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentTicketDetailBinding
import com.sypark.openTicket.model.TicketDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketDetailFragment :
    BaseFragment<FragmentTicketDetailBinding>(R.layout.fragment_ticket_detail) {

    private val args by navArgs<TicketDetailFragmentArgs>()
    private val viewModel: TicketDetailViewModel by viewModels()
    override fun init(view: View) {
        Log.e("!!!!",args.data)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTicketDetailData(args.data)
        }

        viewModel.ticketDetail.observe(this) {
            if (it == null) {

            } else {
                binding.textTitle.text = it.title
            }
        }
    }
}