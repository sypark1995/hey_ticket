package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.navigation.fragment.navArgs
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRegisterValidationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterValidationFragment :
    BaseFragment<FragmentRegisterValidationBinding>(R.layout.fragment_register_validation) {

    private val args by navArgs<RegisterValidationFragmentArgs>()

    override fun init(view: View) {
        binding.apply {
            editEmail.setText(args.item)
            btnNext.setOnClickListener {
                
            }
        }
    }
}