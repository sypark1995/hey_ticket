package com.sypark.openTicket.view.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentLoginFirstBinding
import com.sypark.openTicket.model.LoginFirstViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFirstFragment : BaseFragment<FragmentLoginFirstBinding>(R.layout.fragment_login_first) {

    private val viewModel: LoginFirstViewModel by viewModels()

    override fun init(view: View) {

        viewModel.emailAddress.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                binding.textEmailError.visibility = View.GONE

                binding.btnEmail.setBackgroundResource(R.drawable.round_12_gray)
                binding.btnEmail.isEnabled = false
            } else {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                    //email 맞음!
                    binding.layoutLoginEdit.setBackgroundResource(R.drawable.round_12_gray_white)

                    binding.btnEmail.setBackgroundResource(R.drawable.round_12_black)
                    binding.btnEmail.isEnabled = true

                    binding.textEmailError.visibility = View.GONE
                } else {
                    //email 아님
                    binding.layoutLoginEdit.setBackgroundResource(R.drawable.round_12_red_white)
                    binding.textEmailError.visibility = View.VISIBLE

                    binding.btnEmail.setBackgroundResource(R.drawable.round_12_gray)
                    binding.btnEmail.isEnabled = false
                }
            }
        }

        binding.layoutLoginTop.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnEmail.setOnClickListener {
            findNavController()
        }
        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setEmailAddress(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })




    }

}