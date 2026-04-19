package com.sypark.openTicket.view.fragments

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecommendKeywordBinding
import com.sypark.openTicket.model.ActivityViewModel
import com.sypark.openTicket.model.RecommendKeywordViewModel
import com.sypark.openTicket.view.adapter.RecommendKeywordAdapter

class RecommendKeywordFragment :
    BaseFragment<FragmentRecommendKeywordBinding>(R.layout.fragment_recommend_keyword) {

    private lateinit var recommendKeywordAdapter: RecommendKeywordAdapter
    private var keyWordList = ArrayList<String>()
    private val viewModel: RecommendKeywordViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()

    lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun init(view: View) {

        backPressCallBack()

        FlexboxLayoutManager(view.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.recyclerviewKeyword.apply {
                layoutManager = it
                recommendKeywordAdapter = RecommendKeywordAdapter {
                    keyWordList.remove(it)
                    recommendKeywordAdapter.remove(it)
                }
                adapter = recommendKeywordAdapter
            }
        }


        binding.apply {
            layoutRegisterTop.imgBack.setOnClickListener {
                onBackPressedCallback.handleOnBackPressed()
            }

            btnRegisterKeyword.setOnClickListener {
                keyDown(it.context)
                setKeywordData()
            }

            textKeyword.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    keyDown(v.context)

                    setKeywordData()
                    true
                }

                false
            }

            textKeyword.addTextChangedListener(object : TextWatcher {
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
                    viewModel.setKeywordText(s.toString())
                }
            })

            btnNext.setOnClickListener {
                activityViewModel.setKeyWords(keyWordList)
                binding.includeAgree.root.visibility = View.VISIBLE
            }

            includeAgree.imgClose.setOnClickListener {
                activityViewModel.reSetPushAgree()
                activityViewModel.reSetProvisionAgree()
                activityViewModel.reSetRegisterFinish()

                binding.includeAgree.root.visibility = View.GONE
            }

            includeAgree.checkBoxAgreePush.setOnClickListener {
                activityViewModel.isPushAgree()
                activityViewModel.isRegisterFinish()
            }

            includeAgree.checkBoxProvisionPush.setOnClickListener {
                activityViewModel.isProvisionAgree()
                activityViewModel.isRegisterFinish()
            }
        }

        viewModel.keywordText.observe(this) {
            binding.apply {
                if (it.isEmpty()) {
                    btnRegisterKeyword.isEnabled = false
                    btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_gray)

                } else {
                    btnRegisterKeyword.isEnabled = true
                    btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_black)
                }
            }
        }

        activityViewModel.keywords.observe(viewLifecycleOwner) {
            binding.apply {
                if (it.size == 0) {
                    includeAgree.layoutAgreePush.visibility = View.GONE
                } else {
                    includeAgree.layoutAgreePush.visibility = View.VISIBLE
                }
            }
        }


        activityViewModel.isPushAgree.observe(viewLifecycleOwner) {
            binding.includeAgree.checkBoxAgreePush.isSelected = it
        }
//
        activityViewModel.isProvisionAgree.observe(viewLifecycleOwner) {
            binding.includeAgree.checkBoxProvisionPush.isSelected = it
        }

        activityViewModel.isRegisterAgree.observe(viewLifecycleOwner) {
            binding.apply {
                if (it == true) {
                    includeAgree.btnNext.setBackgroundResource(R.drawable.round_12_black)
                    includeAgree.btnNext.isEnabled = true
                } else {
                    includeAgree.btnNext.setBackgroundResource(R.drawable.round_12_gray)
                    includeAgree.btnNext.isEnabled = false
                }
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    private fun backPressCallBack() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activityViewModel.setGenres()
                activityViewModel.setAreas()
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }
}