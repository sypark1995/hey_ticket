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
import com.sypark.openTicket.databinding.FragmentFavoritesBinding
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.FavoritesViewModel
import com.sypark.openTicket.view.adapter.FavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init(view: View) {
        binding.imgBack.setOnClickListener {
            backPressed()
        }

        adapter = FavoritesAdapter(
            onItemClick = {
                findNavController().navigate(
                    FavoritesFragmentDirections.actionFavoritesFragmentToTicketDetailFragment(it.id)
                )
            },
            onRemoveClick = {
                lifecycleScope.launch {
                    viewModel.removeFavorite(it.id)
                }
            }
        )
        binding.recyclerviewFavorites.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = this@FavoritesFragment.adapter
        }

        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
            if (favorites.isEmpty()) {
                binding.textEmptyTitle.show()
            } else {
                binding.textEmptyTitle.hide()
            }
        }

        lifecycleScope.launch {
            viewModel.loadFavorites()
        }
    }

    override fun backPressed() {
        findNavController().popBackStack()
    }
}
