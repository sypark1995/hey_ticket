package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
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

    @SuppressLint("SetTextI18n")
    override fun init(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.getTicketDetailData((args.item as Ticket).mt20id)
            viewModel.getPlaceDetailData((args.item as Ticket).mt10id)
        }

        viewModel.ticketDetail.observe(this) {
            if (it == null) {

            } else {
                binding.apply {
                    textTitle.text = it.title

                    Glide.with(view.context)
                        .load(it.poster).into(imgPoster)

                    if ((it.startDate.isEmpty() || it.endDate.isEmpty())) {
                        layoutInformationDate.visibility = View.GONE
                    } else {
                        textInformationDate.text = "${it.startDate} ~ ${it.endDate}"
                        textDate.text = "${it.startDate} ~ ${it.endDate}"
                    }

                    if (it.cast.isEmpty()) {
                        layoutInformationCast.visibility = View.GONE
                    } else {
                        textInformationCast.text = it.cast
                    }

                    if (it.pcseguidance.isEmpty()) {
                        layoutInformationPrice.visibility = View.GONE
                    } else {
                        textInformationPrice.text = it.pcseguidance
                    }

                    if (it.place.isEmpty()) {
                        layoutInformationPlace.visibility = View.GONE
                    } else {
                        textInformationPlace.text = it.place
                    }

                    if (it.styurls.isEmpty()) {
                        layoutInformationDetail.visibility = View.GONE
                    } else {
                        //todo_sypark 유섭이한테 이미지 어떻게 뿌렸는지 확인 예정
                    }

                    if (it.entrpsnm.trim().isEmpty()) {
                        layoutInformationHost.visibility = View.GONE
                    } else {
                        textInformationHost.text = it.entrpsnm
                    }

                    if (it.age.isEmpty()) {
                        layoutInformationAge.visibility = View.GONE
                    } else {
                        textInformationAge.text = it.age
                    }

                }
            }
        }
    }
}