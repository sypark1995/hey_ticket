package com.sypark.openTicket.view.fragments

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentLoginSecondBinding
import com.sypark.openTicket.excensions.onTextChanged
import com.sypark.openTicket.model.LoginSecondViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSecondFragment :
    BaseFragment<FragmentLoginSecondBinding>(R.layout.fragment_login_second) {

    private val args by navArgs<LoginSecondFragmentArgs>()
    private val viewModel: LoginSecondViewModel by viewModels()

    override fun init(view: View) {
        setUpObserver()

        binding.apply {
            editEmail.setText(args.item)

            layoutLoginTop.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            editPw.onTextChanged {
                viewModel.setEmailPw(it)
            }

            btnLogin.setOnClickListener {
                //todo_sypark 작업 예정
                if (Common.byPass) {
                    textPwSample.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                } else {
                    textPwSample.setTextColor(
                        ContextCompat.getColor(
                            it.context,
                            R.color.red_FF334B
                        )
                    )
                }
            }

            textFindPw.setOnClickListener {
                findNavController().navigate(
                    LoginSecondFragmentDirections.actionLoginSecondFragmentToLoginFindPwFragment(
                        args.item
                    )
                )
            }
        }
    }

    private fun setUpObserver() {
        viewModel.emailPw.observe(viewLifecycleOwner, ::emailPwWatcher)
    }

    private fun emailPwWatcher(pw: String) {
        if (Common.setPattern(pw)) {
            binding.btnLogin.apply {
                setBackgroundResource(R.drawable.round_12_black)
                isEnabled = true
            }
        } else {
            binding.btnLogin.apply {
                setBackgroundResource(R.drawable.round_12_gray)
                isEnabled = false
            }
        }
    }

    override fun backPressed() {

    }

    private fun hideKeyboard(context: Context) {
        val inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}