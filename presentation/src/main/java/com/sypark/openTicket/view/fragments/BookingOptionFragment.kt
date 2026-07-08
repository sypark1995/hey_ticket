package com.sypark.openTicket.view.fragments

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentBookingOptionBinding
import com.sypark.openTicket.model.BookingVendor
import com.sypark.openTicket.view.adapter.BookingOptionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingOptionFragment :
    BaseFragment<FragmentBookingOptionBinding>(R.layout.fragment_booking_option) {

    private val args by navArgs<BookingOptionFragmentArgs>()
    private lateinit var adapter: BookingOptionAdapter

    override fun init(view: View) {
        adapter = BookingOptionAdapter { vendor ->
            val url = vendor.searchUrl(args.performanceTitle)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.recyclerviewBookingOption.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = this@BookingOptionFragment.adapter
        }
        adapter.submitList(BookingVendor.values().toList())
    }

    override fun backPressed() {
        findNavController().popBackStack()
    }
}
