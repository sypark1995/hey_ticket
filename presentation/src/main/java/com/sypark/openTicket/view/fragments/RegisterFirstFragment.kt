package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.RegisterValidationVerify
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRegisterFirstBinding
import com.sypark.openTicket.model.ActivityViewModel
import com.sypark.openTicket.model.RegisterFirstViewModel
import com.sypark.openTicket.util.OnBackPressedRepository
import com.sypark.openTicket.util.OnBackPressedRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.sql.Timestamp
import java.text.SimpleDateFormat

@AndroidEntryPoint
class RegisterFirstFragment :
    BaseFragment<FragmentRegisterFirstBinding>(R.layout.fragment_register_first),
    OnBackPressedRepository by OnBackPressedRepositoryImpl() {

    val viewModel: RegisterFirstViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val args by navArgs<RegisterFirstFragmentArgs>()

    lateinit var onBackPressedCallback: OnBackPressedCallback

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
                    viewModel.isTimeOut(true)

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
        backPressCallBack()

        binding.apply {
            layoutLoginTop.topTitle.setText(R.string.register_top)
            editEmail.setText(args.item)

            layoutLoginTop.imgBack.setOnClickListener {
                onBackPressedCallback.handleOnBackPressed()
            }

            textEmailCodeAgain.setOnClickListener {
                viewModel.isTimeOut(false)
            }

            btnNext.setOnClickListener {
                activityViewModel.setEmail(args.item)
                activityViewModel.setVerificationCode(viewModel.emailCode.value!!)

                viewModel.getRegisterValidationVerify(
                    registerValidationVerify = RegisterValidationVerify(
                        email = args.item,
                        code = viewModel.emailCode.value!!
                    )
                )
            }

            editCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

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
        viewModel.isTimeOut.observe(viewLifecycleOwner, ::isTimeOutWatcher)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.response.collectLatest(::apiWatcher)
        }
    }

    private fun apiWatcher(apiResult: ApiResult<BaseResponse>) {
        when (apiResult) {
            is ApiResult.Success -> {
                if (apiResult.value.data == null) {

                } else {
                    val data = Gson().fromJson<String?>(
                        apiResult.value.data, object : TypeToken<String?>() {}.type
                    )
                    if (data.isNullOrEmpty()) {
                        viewModel.setEmailErrorCode(true)
                    } else {
                        viewModel.setEmailErrorCode(false)
                        findNavController().navigate(RegisterFirstFragmentDirections.actionRegisterFirstFragmentToRegisterPasswordFragment())
                    }
                }
                Log.e("!!!!", "Success")
            }
            is ApiResult.Error -> {

                Log.e("!!!!", "Error")
            }
            is ApiResult.Loading -> {
                Log.e("!!!!", "loading")
            }
        }
    }

    private fun emailCodeWatcher(emailCode: String) {
        //todo_sypark test
//        if (emailCode.length == 6) {
        binding.apply {
            btnNext.isEnabled = true
            btnNext.setBackgroundResource(R.drawable.round_12_black)
        }
//        } else {
//            binding.apply {
//                btnNext.isEnabled = false
//                btnNext.setBackgroundResource(R.drawable.round_12_gray)
//            }
//        }
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

    private fun isTimeOutWatcher(isTimeOut: Boolean) {
        if (isTimeOut) {
            context?.let {
                binding.apply {
                    layoutCodeEdit.setBackgroundResource(R.drawable.round_12_gray_white)

                    editCode.setText("")
                    editCode.isEnabled = false

                    textEmailError.visibility = View.VISIBLE
                    textEmailError.text =
                        getString(R.string.register_send_email_timer_out)
                    textEmailError.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.gray_949494
                        )
                    )

                    textEmailCodeAgain.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.blue_2C70F2
                        )
                    )
                }
            }
        } else {
            context?.let {
                binding.apply {
                    countDownTimer.start()
                    Toast.makeText(
                        it,
                        getString(R.string.register_send_email_code_again_toast),
                        Toast.LENGTH_SHORT
                    ).show()

                    layoutCodeEdit.setBackgroundResource(R.drawable.round_12_gray_white)

                    editCode.setText("")
                    editCode.isEnabled = true

                    textEmailError.visibility = View.GONE
                    textEmailError.text = getString(R.string.register_send_email_code_error)
                    textEmailError.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.red_FF334B
                        )
                    )

                    textEmailCodeAgain.setTextColor(
                        ContextCompat.getColor(
                            it,
                            R.color.gray_949494
                        )
                    )

                    btnNext.isEnabled = false
                    btnNext.setBackgroundResource(R.drawable.round_12_gray)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        countDownTimer.cancel()
    }

    private fun backPressCallBack() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }


    //todo_sypark 리팩토링 예정
//    private fun restartTimer() {
//        countDownTimer.cancel()
//        viewModel.resetCountDown()
//        countDownTimer.start()
//    }
}