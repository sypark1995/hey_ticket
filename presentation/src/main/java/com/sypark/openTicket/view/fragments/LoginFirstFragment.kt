package com.sypark.openTicket.view.fragments

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentLoginFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFirstFragment : BaseFragment<FragmentLoginFirstBinding>(R.layout.fragment_login_first) {

    override fun init(view: View) {
        binding.layoutLoginTop.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s.toString().let {
                    if (s != null) {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                            //email 맞음!
                            binding.layoutLoginEdit.isErrorEnabled = false
                        } else {
                            Log.e("!!!!",s.toString())
                            //email 아님!
                            binding.layoutLoginEdit.isErrorEnabled = true
                            binding.layoutLoginEdit.error = "올바른 이메일을 입력해주세요"
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

}