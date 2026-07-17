package com.sypark.openTicket.view.fragments

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecentlyViewedBinding
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.RecentlyViewedViewModel
import com.sypark.openTicket.view.adapter.RecentlyViewedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentlyViewedFragment : BaseFragment<FragmentRecentlyViewedBinding>(R.layout.fragment_recently_viewed) {

    private val viewModel: RecentlyViewedViewModel by viewModels()
    private lateinit var adapter: RecentlyViewedAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init(view: View) {
        binding.imgBack.setOnClickListener {
            backPressed()
        }

        adapter = RecentlyViewedAdapter(
            onItemClick = {
                findNavController().navigate(
                    RecentlyViewedFragmentDirections.actionRecentlyViewedFragmentToTicketDetailFragment(it.id)
                )
            }
        )
        binding.recyclerviewRecentlyViewed.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = this@RecentlyViewedFragment.adapter
        }

        viewModel.recentlyViewed.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            if (items.isEmpty()) {
                binding.textEmptyTitle.show()
            } else {
                binding.textEmptyTitle.hide()
            }
        }

        lifecycleScope.launch {
            viewModel.loadRecentlyViewed()
        }
    }

    override fun backPressed() {
        findNavController().popBackStack()
    }
}
