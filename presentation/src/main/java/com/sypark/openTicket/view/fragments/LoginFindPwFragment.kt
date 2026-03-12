package com.sypark.openTicket.view.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentFindPwBinding
import com.sypark.openTicket.model.LoginFindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFindPwFragment : BaseFragment<FragmentFindPwBinding>(R.layout.fragment_find_pw) {

    private val args by navArgs<LoginFindPwFragmentArgs>()
    private val viewModel: LoginFindViewModel by viewModels()

    override fun init(view: View) {
        binding.editEmail.setText(args.item)

        viewModel.setEmail(args.item)

        binding.layoutLoginTop.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewModel.email.observe(this) {
            if (it.isEmpty()) {
                binding.layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                binding.textEmailError.visibility = View.GONE

                binding.btnNext.setBackgroundResource(R.drawable.round_12_gray)
                binding.btnNext.isEnabled = false
            } else {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
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
    }

}