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
            editEmail.setText(args.item)

            layoutLoginTop.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

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
            binding.layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)
            binding.textEmailError.visibility = View.GONE

            binding.btnNext.setBackgroundResource(R.drawable.round_12_gray)
            binding.btnNext.isEnabled = false
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                //email 맞음!
                binding.layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)

                binding.btnNext.setBackgroundResource(R.drawable.round_12_black)
                binding.btnNext.isEnabled = true

                binding.textEmailError.visibility = View.GONE
            } else {
                //email 아님!
                binding.layoutLoginEdit.setBackgroundResource(R.drawable.round_12_red_white)
                binding.textEmailError.visibility = View.VISIBLE

                binding.btnNext.setBackgroundResource(R.drawable.round_12_gray)
                binding.btnNext.isEnabled = false
            }
        }
    }

    override fun backPressed() {

    }

}