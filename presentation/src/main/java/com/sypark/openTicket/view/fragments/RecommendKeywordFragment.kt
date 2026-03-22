package com.sypark.openTicket.view.fragments

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecommendKeywordBinding
import com.sypark.openTicket.model.RecommendKeywordViewModel
import com.sypark.openTicket.view.adapter.RecommendKeywordAdapter

class RecommendKeywordFragment :
    BaseFragment<FragmentRecommendKeywordBinding>(R.layout.fragment_recommend_keyword) {

    private lateinit var recommendKeywordAdapter: RecommendKeywordAdapter
    private var keyWordList = ArrayList<String>()
    private val viewModel: RecommendKeywordViewModel by viewModels()

    override fun init(view: View) {
        FlexboxLayoutManager(view.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.recyclerviewKeyword.apply {
                layoutManager = it
                recommendKeywordAdapter = RecommendKeywordAdapter {
                    Log.e("!!!", it.toString())
                }
                adapter = recommendKeywordAdapter
            }
        }

        binding.textKeyword.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                keyDown(v.context)

                setKeywordData()
                true
            }

            false
        }

        binding.textKeyword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.setKeywordText(s.toString())
            }
        })

        viewModel.keywordText.observe(this) {
            if (it.isEmpty()) {
                binding.btnRegisterKeyword.isEnabled = false
                binding.btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_gray)
            } else {
                binding.btnRegisterKeyword.isEnabled = true
                binding.btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_black)
            }
        }

        binding.btnRegisterKeyword.setOnClickListener {
            keyDown(it.context)
            setKeywordData()
        }

        binding.layoutAgreePush.setOnClickListener {
            viewModel.isPushAgree()
        }

        binding.btnNext.setOnClickListener {

        }

        viewModel.isPushAgree.observe(this) {
            binding.checkboxAgreePush.isSelected = it
        }

    }

    private fun keyDown(view: Context) {
        //키보드 내리기
        val imm = view.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.textKeyword.windowToken, 0)
    }

    private fun setKeywordData() {
        if (!viewModel.keywordText.value.isNullOrEmpty()) {
            keyWordList.add(viewModel.keywordText.value.toString())
            recommendKeywordAdapter.add(viewModel.keywordText.value.toString())
        }
    }

}