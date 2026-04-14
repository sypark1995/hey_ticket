package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRegisterFirstBinding
import com.sypark.openTicket.model.RegisterFirstViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Timestamp
import java.text.SimpleDateFormat

@AndroidEntryPoint
class RegisterFirstFragment :
    BaseFragment<FragmentRegisterFirstBinding>(R.layout.fragment_register_first) {

    val viewModel: RegisterFirstViewModel by viewModels()
    private val args by navArgs<RegisterFirstFragmentArgs>()

    private val countDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(REFRESH_TIME, REFRESH_TIME_INTERVAL) {
            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onTick(millisUntilFinished: Long) {
                val date = SimpleDateFormat("mm:ss").format(Timestamp(millisUntilFinished))

                binding.textEmailCodeTimer.text =
                    getString(R.string.register_send_email_timer) + " " + date
            }

            override fun onFinish() {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    viewModel.resetEmailCode()

                    context?.let {
                        binding.apply {
                            layoutCodeEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                            textEmailError.setTextColor(
                                ContextCompat.getColor(
                                    it,
                                    R.color.gray_949494
                                )
                            )
                            textEmailError.visibility = View.VISIBLE
                            textEmailError.text =
                                getString(R.string.register_send_email_timer_out)
                            textEmailCodeAgain.setTextColor(
                                ContextCompat.getColor(
                                    it,
                                    R.color.blue_2C70F2
                                )
                            )
                            editCode.setText("")
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val REFRESH_TIME = 180_000L
        const val REFRESH_TIME_INTERVAL = 1_000L
    }

    override fun init(view: View) {
        countDownTimer.start()
        setUpObserver()

        binding.apply {
            layoutLoginTop.topTitle.setText(R.string.register_top)
            editEmail.setText(args.item)

            layoutLoginTop.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            textEmailCodeAgain.setOnClickListener {
                binding.btnNext.isEnabled = false
                binding.btnNext.setBackgroundResource(R.drawable.round_12_gray)

                binding.editCode.setText("")

                context?.let {
                    Toast.makeText(
                        it,
                        getString(R.string.register_send_email_code_again_toast),
                        Toast.LENGTH_SHORT
                    ).show()

                    countDownTimer.start()
                    binding.textEmailError.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.red_FF334B
                        )
                    )
                    binding.layoutCodeEdit.setBackgroundResource(R.drawable.round_12_gray_white)
                    binding.textEmailError.visibility = View.GONE
                    binding.textEmailError.text =
                        getString(R.string.register_send_email_code_error)

                    binding.textEmailCodeAgain.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.gray_949494
                        )
                    )
                }
            }

            btnNext.setOnClickListener {
                // todo_sypark
                if (false) {
                    //인증 번호 미일치
                    viewModel.setEmailErrorCode(true)
                } else {
                    viewModel.setEmailErrorCode(false)
                    findNavController().navigate(RegisterFirstFragmentDirections.actionRegisterFirstFragmentToRegisterPasswordFragment())
                }
            }

            editCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.setEmailCode(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    private fun setUpObserver() {
        viewModel.emailCode.observe(viewLifecycleOwner, ::emailCodeWatcher)
        viewModel.isEmailCodeError.observe(viewLifecycleOwner, ::emailCodeErrorWatcher)
    }

    private fun emailCodeWatcher(emailCode: String) {
        if (emailCode.length == 6) {
            binding.apply {
                btnNext.isEnabled = true
                btnNext.setBackgroundResource(R.drawable.round_12_black)
            }
        } else {
            binding.apply {
                btnNext.isEnabled = false
                btnNext.setBackgroundResource(R.drawable.round_12_gray)
            }
        }
    }

    private fun emailCodeErrorWatcher(isError: Boolean) {
        if (isError) {
            binding.apply {
                textEmailError.visibility = View.VISIBLE
                layoutCodeEdit.setBackgroundResource(R.drawable.round_12_red_white)
            }
        } else {
            binding.apply {
                textEmailError.visibility = View.GONE
                layoutCodeEdit.setBackgroundResource(R.drawable.round_12_gray_white)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer.cancel()
    }


    //todo_sypark 리팩토링 예정
//    private fun restartTimer() {
//        countDownTimer.cancel()
//        viewModel.resetCountDown()
//        countDownTimer.start()
//    }
}