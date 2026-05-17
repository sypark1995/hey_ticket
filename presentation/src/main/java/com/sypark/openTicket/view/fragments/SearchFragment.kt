package com.sypark.openTicket.view.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.data.util.Util
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentSearchBinding
import com.sypark.openTicket.excensions.afterTextChanged
import com.sypark.openTicket.excensions.dpToPx
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.SearchViewModel
import com.sypark.openTicket.popups.showSearchDeletePopup
import com.sypark.openTicket.view.adapter.RankingMoreAdapter
import com.sypark.openTicket.view.adapter.SearchWordListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val categorySearchViewModel: SearchViewModel by viewModels()
    lateinit var searchWordListAdapter: SearchWordListAdapter
    private lateinit var searchAdapter: RankingMoreAdapter
    override fun init(view: View) {

        binding.apply {
            imgClose.setOnClickListener {
                findNavController().popBackStack()
            }

            textSearch.apply {
                afterTextChanged {
                    if (it.isEmpty()) {
                        layoutRecentlySearch.show()
                        layoutSearchList.hide()
                    } else {
                        layoutRecentlySearch.hide()
                    }
                }

                setOnKeyListener { v, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        categorySearchViewModel.setWord(textSearch.text.trim().toString())
                        true
                    }
                    false
                }
            }

            recyclerviewRecentlySearch.apply {
                searchWordListAdapter = SearchWordListAdapter {
                    Log.e("SearchWordListAdapter", it.toString())
                    layoutRecentlySearch.hide()
                    textSearch.setText(it)
                    categorySearchViewModel.setWord(it)
                    apiWatcher(it)
                }

                adapter = searchWordListAdapter
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                layoutManager = LinearLayoutManager(
                    this@SearchFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            textDelete.setOnClickListener {
                activity?.showSearchDeletePopup({
                    Log.e("!!!!", "취소")
                }, {
                    Log.e("!!!!", "확인")
                    categorySearchViewModel.deleteAllWords()
                })
            }

            categorySearchViewModel.allWords.observe(viewLifecycleOwner) { words ->
                words.let {
                    binding.apply {
                        if (it.isEmpty()) {
                            layoutRecentlySearch.hide()
                        }
                        searchWordListAdapter.submitList(it)
                    }
                }
            }

            radioPerformance.setOnClickListener {
                categorySearchViewModel.setRadioButton(Util.SearchType.PERFORMANCE)
            }

            radioArtist.setOnClickListener {
                categorySearchViewModel.setRadioButton(Util.SearchType.ARTIST)
            }

            recyclerviewSearch.apply {
                layoutManager = LinearLayoutManager(view.context)
                searchAdapter = RankingMoreAdapter {
                    findNavController().navigate(
                        SearchFragmentDirections.actionSearchFragmentToTicketDetailFragment(
                            it
                        )
                    )
                }
                adapter = searchAdapter
            }

            categorySearchViewModel.searchWord.observe(viewLifecycleOwner, ::apiWatcher)
        }

        categorySearchViewModel.radioButton.observe(viewLifecycleOwner) {
            if (it.name.isEmpty()) {
                Log.e("!!!!", "empty")
            } else {
                if (categorySearchViewModel.searchWord.value != null) {
                    apiWatcher(categorySearchViewModel.searchWord.value)
                } else {
                    //todo empty layout show()
                }
            }
        }
    }

    private fun apiWatcher(searchWord: String?) {
        binding.apply {
            Log.e("", "apiWatcher")
            if (searchWord.isNullOrEmpty()) {
                layoutSearchList.hide()
            } else {
                lifecycleScope.launchWhenCreated {
                    categorySearchViewModel.setSearch(
                        searchWord, categorySearchViewModel.radioButton.value!!
                    ).collectLatest {
//                        it.map { data ->
//                            textSearchCount.text = data.second.toString()
//                        }
                        searchAdapter.submitData(PagingData.empty())
                        searchAdapter.submitData(it.map { data ->
                            data.first
                        })
                    }
                }

                layoutSearchList.show()
                categorySearchViewModel.insertWord(
                    searchWord,
                    Calendar.getInstance().time
                )


            }

            (context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?).apply {
                this!!.hideSoftInputFromWindow(
                    view?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    override fun backPressed() {
        findNavController().popBackStack()
    }
}