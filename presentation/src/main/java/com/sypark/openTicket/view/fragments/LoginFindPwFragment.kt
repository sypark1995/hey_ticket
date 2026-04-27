package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentFindPwBinding
import com.sypark.openTicket.excensions.onTextChanged
import com.sypark.openTicket.model.LoginFindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFindPwFragment : BaseFragment<FragmentFindPwBinding>(R.layout.fragment_find_pw) {

    private val args by navArgs<LoginFindPwFragmentArgs>()
    private val viewModel: LoginFindViewModel by viewModels()

    override fun init(view: View) {
        setUpObserver()

        viewModel.setEmail(args.item)
        binding.apply {
            layoutLoginTop.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            editEmail.setText(args.item)
            editEmail.onTextChanged {
                viewModel.setEmail(it)
            }
        }
    }

    private fun setUpObserver() {
        viewModel.email.observe(viewLifecycleOwner, ::emailWatcher)
    }

    private fun emailWatcher(email: String) {
        if (email.isEmpty()) {
            binding.apply {
                layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                textEmailError.visibility = View.GONE

                btnNext.setBackgroundResource(R.drawable.round_12_gray)
                btnNext.isEnabled = false
            }
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                //email 맞음!
                binding.apply {
                    layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                    textEmailError.visibility = View.GONE

                    btnNext.setBackgroundResource(R.drawable.round_12_black)
                    btnNext.isEnabled = true
                }
            } else {
                //email 아님!
                binding.apply {
                    layoutLoginEdit.setBackgroundResource(R.drawable.round_12_red_white)
                    textEmailError.visibility = View.VISIBLE

                    btnNext.setBackgroundResource(R.drawable.round_12_gray)
                    btnNext.isEnabled = false
                }
            }
        }
    }

    override fun backPressed() {

    }

}