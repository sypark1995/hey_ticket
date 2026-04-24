package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRegisterPasswordBinding
import com.sypark.openTicket.excensions.onTextChanged
import com.sypark.openTicket.model.ActivityViewModel
import com.sypark.openTicket.popups.showClosePopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPasswordFragment :
    BaseFragment<FragmentRegisterPasswordBinding>(R.layout.fragment_register_password) {

    private val activityViewModel: ActivityViewModel by activityViewModels()

    @SuppressLint("ShowToast")
    override fun init(view: View) {

        setUpObserver()

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

            editPw.onTextChanged {
                activityViewModel.setPw(it)
            }

            layoutRegisterTop.imgBack.setOnClickListener {
                backPressed()
            }

            layoutRegisterTop.imgClose.setOnClickListener {
                backPressed()
            }
        }
    }

    private fun setUpObserver() {
        activityViewModel.pw.observe(viewLifecycleOwner, ::pwWatcher)
    }

    private fun pwWatcher(pw: String?) {
        pw?.let {
            binding.apply {
                if (Common.setPattern(pw)) {
                    btnNext.setBackgroundResource(R.drawable.round_12_black)
                    btnNext.isEnabled = true

                    layoutPwEdit.setBackgroundResource(
                        R.drawable.round_12_gray_white
                    )
                    textPwSample.setTextColor(
                        ContextCompat.getColor(
                            root.context,
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
                            root.context,
                            R.color.red_FF334B
                        )
                    )
                }
            }
        }
    }

    override fun backPressed() {
        requireActivity().showClosePopup(getString(R.string.register_close_popup), {

        }, {
            findNavController().popBackStack()
        })
    }
}