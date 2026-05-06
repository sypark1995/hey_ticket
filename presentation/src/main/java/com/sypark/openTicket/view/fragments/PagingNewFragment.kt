package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.data.util.Util
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentPagingNewBinding
import com.sypark.openTicket.excensions.dpToPx
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.NewViewModel
import com.sypark.openTicket.view.adapter.GenreAdapter
import com.sypark.openTicket.view.adapter.RankingMoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PagingNewFragment :
    BaseFragment<FragmentPagingNewBinding>(R.layout.fragment_paging_new) {

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var rankingMoreAdapter: RankingMoreAdapter

    private val viewModel: NewViewModel by viewModels()

    override fun init(view: View) {
        binding.apply {
            layoutTop.topTitle.text = getString(R.string.main_new_ticket)

            layoutTop.imgBack.setOnClickListener {
                backPressed()
            }

            recyclerviewNewFilter.apply {
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

            recyclerviewNew.apply {
                layoutManager = LinearLayoutManager(view.context)

                rankingMoreAdapter = RankingMoreAdapter { id ->
                    findNavController().navigate(
                        PagingNewFragmentDirections.actionPagingNewFragmentToTicketDetailFragment(id)
                    )
                }

                lifecycleScope.launch {
                    rankingMoreAdapter.loadStateFlow.collectLatest { loadStates ->
//                        includeLoad.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                        Log.e("refresh", loadStates.refresh.toString())

                    }
                }

                adapter = rankingMoreAdapter
                setUpData()
            }

            sortLayout.setOnClickListener {
                viewModel.setShow(true)
            }

            includeNewSort.apply {
                imgClose.setOnClickListener {
                    viewModel.setShow(false)
                }

                rbCreate.setOnClickListener {
                    viewModel.setRadioButton(Util.NewButtonType.CREATED_DATE)
                }

                rbView.setOnClickListener {
                    viewModel.setRadioButton(Util.NewButtonType.VIEWS)
                }

                btnNext.setOnClickListener {
                    viewModel.setShow(false)
                    setUpData()
                }
            }

            viewModel.genre.observe(viewLifecycleOwner, ::apiWatcher)
            viewModel.isShow.observe(viewLifecycleOwner, ::showWatcher)
        }
    }

    private fun rankingFilterItemClicked(position: Int) {
        genreAdapter.setSelectedPosition(position)
    }

    private fun apiWatcher(genreCode: String?) {
        lifecycleScope.launch {
            viewModel.setNew(genreCode.orEmpty(), viewModel.radioButton.value!!).collectLatest {
                rankingMoreAdapter.submitData(it)
            }
        }
    }

    private fun showWatcher(isShow: Boolean) {
        if (isShow) {
            binding.includeNewSort.root.show()
        } else {
            binding.includeNewSort.root.hide()
        }
    }

    private fun setUpData() {
        apiWatcher(viewModel.genre.value.orEmpty())
        textSortState()
    }

    private fun textSortState() {
        binding.textSort.text = when (viewModel.radioButton.value!!) {
            Util.NewButtonType.CREATED_DATE -> {
                getString(R.string.sort_new)
            }

            Util.NewButtonType.VIEWS -> {
                getString(R.string.sort_view)
            }
        }
    }

    override fun backPressed() {
        if (viewModel.isShow.value == true) {
            viewModel.setShow(false)
        } else {
            findNavController().popBackStack()
        }
    }
}