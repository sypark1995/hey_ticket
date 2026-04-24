package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentLoginFirstBinding
import com.sypark.openTicket.excensions.*
import com.sypark.openTicket.model.LoginFirstViewModel
import com.sypark.openTicket.popups.showClosePopup
import com.sypark.openTicket.util.KeyboardVisibilityUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFirstFragment : BaseFragment<FragmentLoginFirstBinding>(R.layout.fragment_login_first) {

    private val viewModel: LoginFirstViewModel by viewModels()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun init(view: View) {
        setUpObserver()

        binding.apply {
            layoutLoginTop.imgBack.setOnClickListener {
                backPressed()
            }

            btnEmail.setOnClickListener {
                viewModel.getLoginValidation()
            }

            editEmail.apply {
                onTextChanged {
                    viewModel.setEmailAddress(it)
                }

                setOnKeyListener()
            }

            keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window,
                onShowKeyboard = { _, _ ->
                    // 키보드가 올라올 때의 동작
                    btnKakao.setMargins(bottom = 10.dpToPx())
                },
                onHideKeyboard = {
                    // 키보드가 내려갈 때의 동작
                    btnKakao.setMargins(bottom = 50.dpToPx())
                }
            )
        }
    }

    override fun backPressed() {
        requireActivity().showClosePopup(getString(R.string.register_close_popup), {

        }, {
            findNavController().popBackStack()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        keyboardVisibilityUtils.detachKeyboardListeners()
    }

    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.response.collectLatest(::apiWatcher)
        }

        viewModel.emailAddress.observe(viewLifecycleOwner, ::isEmailWatcher)
    }

    private fun apiWatcher(apiResult: ApiResult<BaseResponse>) {
        when (apiResult) {
            is ApiResult.Success -> {
                val isLoginValidation = Gson().fromJson<Boolean>(
                    apiResult.value.data, object : TypeToken<Boolean>() {}.type
                )

                if (isLoginValidation) {
                    findNavController().navigate(
                        LoginFirstFragmentDirections.actionLoginFirstFragmentToLoginSecondFragment(
                            viewModel.emailAddress.value.toString()
                        )
                    )
                } else {
                    findNavController().navigate(
                        LoginFirstFragmentDirections.actionLoginFirstFragmentToRegisterValidationFragment(
                            viewModel.emailAddress.value.toString()
                        )
                    )
                }
            }
            is ApiResult.Error -> {

            }
            is ApiResult.Loading -> {
                Log.e("!!!!!", "")
            }
        }
    }

    private fun isEmailWatcher(email: String) {
        binding.apply {
            if (email.isEmpty()) {
                layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                textEmailError.hide()

                btnEmail.setBackgroundResource(R.drawable.round_12_gray)
                btnEmail.isEnabled = false
            } else {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    //email 맞음!
                    layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)

                    btnEmail.setBackgroundResource(R.drawable.round_12_black)
                    btnEmail.isEnabled = true

                    textEmailError.hide()
                } else {
                    //email 아님
                    layoutLoginEdit.setBackgroundResource(R.drawable.round_12_red_white)
                    textEmailError.show()

                    btnEmail.setBackgroundResource(R.drawable.round_12_gray)
                    btnEmail.isEnabled = false
                }
            }
        }
    }

}