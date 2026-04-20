package com.sypark.openTicket.view.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentLoginFirstBinding
import com.sypark.openTicket.excensions.dpToPx
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.setMargins
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.LoginFirstViewModel
import com.sypark.openTicket.util.KeyboardVisibilityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFirstFragment : BaseFragment<FragmentLoginFirstBinding>(R.layout.fragment_login_first) {

    private val viewModel: LoginFirstViewModel by viewModels()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun init(view: View) {
        binding.apply {
            viewModel.emailAddress.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                    textEmailError.hide()

                    btnEmail.setBackgroundResource(R.drawable.round_12_gray)
                    btnEmail.isEnabled = false

                } else {

                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
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

            layoutLoginTop.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnEmail.setOnClickListener {
                viewModel.getLoginValidation(::isLoginValidation)
            }

            editEmail.apply {
                addTextChangedListener(object : TextWatcher {
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
                        viewModel.setEmailAddress(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                })

                setOnKeyListener { v, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                        val imm =
                            v.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(this.windowToken, 0)
                    }
                    true
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        keyboardVisibilityUtils.detachKeyboardListeners()
    }

    private fun isLoginValidation(isLoginValidation: Boolean) {
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

}