package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRegisterPasswordBinding
import com.sypark.openTicket.model.ActivityViewModel
import com.sypark.openTicket.popups.showRegisterClosePopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPasswordFragment :
    BaseFragment<FragmentRegisterPasswordBinding>(R.layout.fragment_register_password) {

    private val activityViewModel: ActivityViewModel by activityViewModels()

    lateinit var onBackPressedCallback: OnBackPressedCallback

    @SuppressLint("ShowToast")
    override fun init(view: View) {

        backPressCallBack()
        Toast.makeText(
            view.context,
            getString(R.string.register_email_confirm_finish),
            Toast.LENGTH_SHORT
        ).show()

        binding.apply {
            btnNext.setOnClickListener {
                findNavController().navigate(
                    RegisterPasswordFragmentDirections.actionRegisterPasswordFragmentToRecommendFragment()
                )
            }

            editPw.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    activityViewModel.setPw(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })


            layoutRegisterTop.imgBack.setOnClickListener {
                onBackPressedCallback.handleOnBackPressed()
            }

            layoutRegisterTop.imgClose.setOnClickListener {
                onBackPressedCallback.handleOnBackPressed()
            }
        }

        activityViewModel.pw.observe(this) {

            it?.let {
                binding.apply {
                    if (Common.setPattern(it)) {
                        btnNext.setBackgroundResource(R.drawable.round_12_black)
                        btnNext.isEnabled = true

                        layoutPwEdit.setBackgroundResource(
                            R.drawable.round_12_gray_white
                        )

                        textPwSample.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.gray_949494
                            )
                        )
                    } else {
                        btnNext.setBackgroundResource(R.drawable.round_12_gray)
                        btnNext.isEnabled = false

                        layoutPwEdit.setBackgroundResource(
                            R.drawable.round_12_red_white
                        )

                        textPwSample.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.red_FF334B
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    private fun backPressCallBack() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                requireActivity().showRegisterClosePopup({

                }, {
                    activityViewModel.setEmail()
                    activityViewModel.setVerificationCode()
                    findNavController().popBackStack()
                })
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }
}