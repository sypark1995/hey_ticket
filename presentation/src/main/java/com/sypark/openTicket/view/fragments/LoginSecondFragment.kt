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
import com.sypark.openTicket.model.LoginSecondViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSecondFragment :
    BaseFragment<FragmentLoginSecondBinding>(R.layout.fragment_login_second) {

    private val args by navArgs<LoginSecondFragmentArgs>()
    private val viewModel: LoginSecondViewModel by viewModels()

    override fun init(view: View) {
        binding.editEmail.setText(args.item)

        viewModel.emailPw.observe(viewLifecycleOwner) {
            if (Common.setPattern(it)) {
                binding.btnLogin.setBackgroundResource(R.drawable.round_12_black)
                binding.btnLogin.isEnabled = true
            } else {
                binding.btnLogin.setBackgroundResource(R.drawable.round_12_gray)
                binding.btnLogin.isEnabled = false
            }
        }


        binding.layoutLoginTop.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setEmailPw(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.btnLogin.setOnClickListener {
            if (Common.byPass) {
                binding.textPwSample.setTextColor(ContextCompat.getColor(it.context, R.color.black))
            } else {
                binding.textPwSample.setTextColor(
                    ContextCompat.getColor(
                        it.context,
                        R.color.red_FF334B
                    )
                )
            }
        }

        binding.textFindPw.setOnClickListener {
            findNavController().navigate(
                LoginSecondFragmentDirections.actionLoginSecondFragmentToLoginFindPwFragment(
                    args.item
                )
            )
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