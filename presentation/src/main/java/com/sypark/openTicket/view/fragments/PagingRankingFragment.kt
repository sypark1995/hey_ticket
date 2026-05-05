package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.data.util.Util
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentPagingRankingBinding
import com.sypark.openTicket.excensions.dpToPx
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.RankingViewModel
import com.sypark.openTicket.view.adapter.GenreAdapter
import com.sypark.openTicket.view.adapter.RankingMoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PagingRankingFragment :
    BaseFragment<FragmentPagingRankingBinding>(R.layout.fragment_paging_ranking) {

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var rankingMoreAdapter: RankingMoreAdapter

    private val viewModel: RankingViewModel by viewModels()

    override fun init(view: View) {
        binding.apply {
            layoutTop.topTitle.text = getString(R.string.main_ranking)

            layoutTop.imgBack.setOnClickListener {
                backPressed()
            }

            textSortState()

            recyclerviewRankingFilter.apply {
                genreAdapter = GenreAdapter { position, item ->
                    rankingFilterItemClicked(position)
                    viewModel.setGenre(item.code)
                }

                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))

                genreAdapter.submitList(Common.genreList)
                adapter = genreAdapter
                val pos = if (viewModel.genre.value.orEmpty().isEmpty()) {
                    0
                } else {
                    Common.genreList.indexOfFirst {
                        it.code == viewModel.genre.value
                    }
                }

                genreAdapter.setSelectedPosition(pos)
            }

            recyclerviewRanking.apply {
                layoutManager = LinearLayoutManager(view.context)
                rankingMoreAdapter = RankingMoreAdapter { id ->
                    findNavController().navigate(
                        PagingRankingFragmentDirections.actionPagingRankingFragmentToTicketDetailFragment(
                            id
                        )
                    )
                }
//                rankingMoreAdapter.withLoadStateFooter(
//                    PagingLoadStateAdapter { rankingMoreAdapter.retry() }
//                )
                adapter = rankingMoreAdapter
            }

            sortLayout.setOnClickListener {
                viewModel.setShow(true)
            }

            includeSort.imgClose.setOnClickListener {
                viewModel.setShow(false)
            }

            includeSort.rbDay.setOnClickListener {
                viewModel.setRadioButton(Util.ButtonType.DAY)
            }

            includeSort.rbWeek.setOnClickListener {
                viewModel.setRadioButton(Util.ButtonType.WEEK)
            }

            includeSort.rbMonth.setOnClickListener {
                viewModel.setRadioButton(Util.ButtonType.MONTH)
            }

            includeSort.btnNext.setOnClickListener {
                viewModel.setShow(false)

                lifecycleScope.launch {
                    viewModel.setRanking(
                        viewModel.genre.value.orEmpty(),
                        viewModel.radioButton.value!!
                    ).collectLatest {
                        rankingMoreAdapter.submitData(it)
                    }
                }

                textSortState()
            }

            viewModel.genre.observe(viewLifecycleOwner, ::apiWatcher)
            viewModel.isShow.observe(viewLifecycleOwner, ::showWatcher)

            lifecycleScope.launch {
                viewModel.setRanking(viewModel.genre.value.orEmpty(), viewModel.radioButton.value!!)
                    .collectLatest {
                        rankingMoreAdapter.submitData(it)
                    }
            }
        }
    }

    private fun apiWatcher(genreCode: String?) {
        lifecycleScope.launch {
            viewModel.setRanking(genreCode.orEmpty(), viewModel.radioButton.value!!).collectLatest {
                rankingMoreAdapter.submitData(it)
            }
        }
    }

    private fun showWatcher(isShow: Boolean) {
        if (isShow) {
            binding.includeSort.root.show()
        } else {
            binding.includeSort.root.hide()
        }
    }

    override fun backPressed() {
        if (viewModel.isShow.value == true) {
            viewModel.setShow(false)
        } else {
            findNavController().popBackStack()
        }
    }

    private fun textSortState() {
        binding.textSort.text = when (viewModel.radioButton.value!!) {
            Util.ButtonType.DAY -> {
                getString(R.string.sort_day)
            }

            Util.ButtonType.WEEK -> {
                getString(R.string.sort_week)
            }

            Util.ButtonType.MONTH -> {
                getString(R.string.sort_month)
            }
        }
    }

    private fun rankingFilterItemClicked(position: Int) {
        genreAdapter.setSelectedPosition(position)
    }
}