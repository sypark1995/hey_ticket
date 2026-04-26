package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRankingBinding
import com.sypark.openTicket.excensions.dpToPx
import com.sypark.openTicket.model.RankingViewModel
import com.sypark.openTicket.view.adapter.GenreAdapter
import com.sypark.openTicket.view.adapter.RankingMoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var rankingMoreAdapter: RankingMoreAdapter

    private val viewModel: RankingViewModel by viewModels()

    override fun init(view: View) {
        binding.apply {
            layoutTop.topTitle.text = getString(R.string.main_ranking)

            layoutTop.imgBack.setOnClickListener {
                backPressed()
            }

            recyclerviewRankingFilter.apply {
                genreAdapter = GenreAdapter { position, item ->
                    rankingFilterItemClicked(position)
                    viewModel.setGenre(item.code)
                }

                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))

                genreAdapter.submitList(Common.genreList)
                adapter = genreAdapter
                genreAdapter.setSelectedPosition(0)
            }

            recyclerviewRanking.apply {
                layoutManager = LinearLayoutManager(view.context)
                rankingMoreAdapter = RankingMoreAdapter {

                }
                adapter = rankingMoreAdapter
            }

            viewModel.genre.observe(viewLifecycleOwner, ::apiWatcher)

            lifecycleScope.launch {
                viewModel.setRanking(viewModel.genre.value.orEmpty()).collectLatest {
                    try {
                        rankingMoreAdapter.submitData(it)
                    } catch (e: Exception) {
                        Log.e("Exception", e.toString())
                    }
                }
            }
        }
    }

    private fun apiWatcher(genreCode: String?) {
        lifecycleScope.launch {
            viewModel.setRanking(genreCode.orEmpty()).collectLatest {
                rankingMoreAdapter.submitData(it)
            }
        }
    }

    override fun backPressed() {
        findNavController().popBackStack()
    }

    private fun rankingFilterItemClicked(position: Int) {
        genreAdapter.setSelectedPosition(position)
    }
}