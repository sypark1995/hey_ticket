package com.sypark.openTicket.view.fragments

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.RegisterToken
import com.sypark.data.db.entity.request.Signup
import com.sypark.openTicket.PREFERENCE_KEY_ACCESS_TOKEN
import com.sypark.openTicket.PREFERENCE_KEY_GRANT_TYPE
import com.sypark.openTicket.PREFERENCE_KEY_REFRESH_TOKEN
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecommendKeywordBinding
import com.sypark.openTicket.model.ActivityViewModel
import com.sypark.openTicket.model.RecommendKeywordViewModel
import com.sypark.openTicket.util.AppPreference
import com.sypark.openTicket.view.adapter.RecommendKeywordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
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

            includeAgree.btnNext.setOnClickListener {
                val genreList = if (activityViewModel.genres.value.isNullOrEmpty()) {
                    arrayListOf()
                } else {
                    activityViewModel.genres.value!!.map { data ->
                        data.code
                    } as ArrayList<String>
                }

                val areaList = if (activityViewModel.areas.value.isNullOrEmpty()) {
                    arrayListOf()
                } else {
                    activityViewModel.areas.value!!.map { data ->
                        data.code
                    } as ArrayList<String>
                }

                viewModel.getSignUp(
                    signup = Signup(
                        email = activityViewModel.email.value!!,
                        password = activityViewModel.pw.value!!,
                        verificationCode = activityViewModel.verificationCode.value!!,
                        genres = genreList,
                        areas = areaList,
                        keywords = activityViewModel.keywords.value!!,
                        keywordPush = activityViewModel.isPushAgree.value!!
                    )
                )
            }
        }

        viewModel.keywordText.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.btnRegisterKeyword.isEnabled = false
                binding.btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_gray)

            } else {
                binding.btnRegisterKeyword.isEnabled = true
                binding.btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_black)
            }
        }

        activityViewModel.keywords.observe(viewLifecycleOwner) {
            binding.apply {
                if (it.isNullOrEmpty()) {
                    includeAgree.layoutAgreePush.visibility = View.GONE
                } else {
                    includeAgree.layoutAgreePush.visibility = View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.response.collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val data = Gson().fromJson<RegisterToken>(
                            result.value.data, object : TypeToken<RegisterToken>() {}.type
                        )

                        //todo_sypark response code 로 수정 예정
                        if (result.value.code == "OK") {
                            AppPreference.set(PREFERENCE_KEY_GRANT_TYPE, data.grantType)
                            AppPreference.set(PREFERENCE_KEY_ACCESS_TOKEN, data.accessToken)
                            AppPreference.set(PREFERENCE_KEY_REFRESH_TOKEN, data.refreshToken)
                            findNavController().navigate(RecommendKeywordFragmentDirections.actionRecommendKeywordFragmentToMainFragment())
                        }


                    }

                    is ApiResult.Error -> {

                    }

                    is ApiResult.Loading -> {

                    }
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