package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRegisterPasswordBinding
import com.sypark.openTicket.model.RegisterPasswordViewModel
import com.sypark.openTicket.popups.showRegisterClosePopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPasswordFragment :
    BaseFragment<FragmentRegisterPasswordBinding>(R.layout.fragment_register_password) {

    private val viewModel: RegisterPasswordViewModel by viewModels()

    @SuppressLint("ShowToast")
    override fun init(view: View) {

        val toast = Toast.makeText(
            view.context,
            getString(R.string.register_email_confirm_finish),
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.show()

        binding.editPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setEmailCode(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewModel.emailCode.observe(this) {


            if (Common.setPattern(it)) {

                binding.btnNext.setBackgroundResource(R.drawable.round_12_black)
                binding.btnNext.isEnabled = true

                binding.layoutPwEdit.setBackgroundResource(
                    R.drawable.round_12_gray_white
                )

                binding.textPwSample.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.gray_949494
                    )
                )
            } else {
                binding.btnNext.setBackgroundResource(R.drawable.round_12_gray)
                binding.btnNext.isEnabled = false

                binding.layoutPwEdit.setBackgroundResource(
                    R.drawable.round_12_red_white
                )

                binding.textPwSample.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.red_FF334B
                    )
                )
            }
        }

        binding.layoutRegisterTop.imgBack.setOnClickListener {

            activity?.showRegisterClosePopup({

            }, {
                findNavController().popBackStack()
            })
        }

        binding.layoutRegisterTop.imgClose.setOnClickListener {
            activity?.showRegisterClosePopup({

            }, {
                findNavController().popBackStack()
            })
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(RegisterPasswordFragmentDirections.actionRegisterPasswordFragmentToRecommendFragment())
        }
    }
}