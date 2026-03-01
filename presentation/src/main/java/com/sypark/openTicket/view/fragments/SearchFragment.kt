package com.sypark.openTicket.view.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentSearchBinding
import com.sypark.openTicket.model.CategorySearchViewModel
import com.sypark.openTicket.popups.showSearchDeletePopup
import com.sypark.openTicket.view.SearchWordListAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val categorySearchViewModel: CategorySearchViewModel by viewModels()
    lateinit var searchWordListAdapter: SearchWordListAdapter
    override fun init(view: View) {

        binding.apply {
            imgClose.setOnClickListener {
                findNavController().popBackStack()
            }

            textSearch.apply {
                this.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        categorySearchViewModel.setWord(s.toString())
                    }
                })

                setOnKeyListener { v, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        if (!categorySearchViewModel.searchWord.value.isNullOrEmpty()) {
                            categorySearchViewModel.insertWord(
                                categorySearchViewModel.searchWord.value!!,
                                Calendar.getInstance().time
                            )
                        }
                        (view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?).apply {
                            this!!.hideSoftInputFromWindow(
                                view.windowToken,
                                InputMethodManager.HIDE_NOT_ALWAYS
                            )
                        }
                        true
                    } else {
                        false
                    }
                }
            }

            recyclerviewRecentlySearch.apply {
                searchWordListAdapter = SearchWordListAdapter()
                adapter = searchWordListAdapter

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
        }

        categorySearchViewModel.allWords.observe(owner = this) { words ->
            words.let {
                if (words.isEmpty()) {
                    binding.layoutRecentlySearch.visibility = View.GONE
                } else {
                    binding.layoutRecentlySearch.visibility = View.VISIBLE
                }

                searchWordListAdapter.submitList(it)
            }
        }

    }
}