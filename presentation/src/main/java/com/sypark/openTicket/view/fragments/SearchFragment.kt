package com.sypark.openTicket.view.fragments

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentSearchBinding
import com.sypark.openTicket.model.CategorySearchViewModel
import com.sypark.openTicket.view.SearchWordListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val categorySearchViewModel: CategorySearchViewModel by viewModels()
    lateinit var searchWordListAdapter: SearchWordListAdapter
    override fun init(view: View) {

        binding.imgClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.textSearch.apply {
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    categorySearchViewModel.setWord(s.toString())
                }
            })
        }

        binding.textSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.e("!!", categorySearchViewModel.searchWord.value.toString())
                if (!categorySearchViewModel.searchWord.value.isNullOrEmpty()) {
                    categorySearchViewModel.insertWord(categorySearchViewModel.searchWord.value!!)
                }
                true
            } else {
                false
            }
        }

        binding.recyclerviewRecentlySearch.apply {
            searchWordListAdapter = SearchWordListAdapter()
            adapter = searchWordListAdapter
            layoutManager = LinearLayoutManager(
                this@SearchFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        categorySearchViewModel.allWords.observe(owner = this) { words ->
            words.let { searchWordListAdapter.submitList(it) }
        }
    }
}