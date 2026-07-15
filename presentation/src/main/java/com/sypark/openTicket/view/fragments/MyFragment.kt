package com.sypark.openTicket.view.fragments

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMyBinding
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {
    private val TAG = "MyFragment"

    private val viewModel: MyViewModel by viewModels()

    override fun init(view: View) {
        binding.layoutBottom.navigationBottom.menu.getItem(2).isChecked = true

        bindProfile()

        binding.btnLogin.setOnClickListener {
            loginWithKakao()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }

        binding.textFavoritesMenu.setOnClickListener {
            findNavController().navigate(MyFragmentDirections.actionMyFragmentToFavoritesFragment())
        }

        refreshLoginState()
    }

    override fun backPressed() {
    }

    private fun bindProfile() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            binding.textNickname.text = it.orEmpty()
        }

        viewModel.profileImageUrl.observe(viewLifecycleOwner) {
            Glide.with(this).load(it).circleCrop().into(binding.imgProfile)
        }
    }

    private fun refreshLoginState() {
        if (AuthApiClient.instance.hasToken()) {
            binding.layoutLoggedIn.show()
            binding.layoutLoggedOut.hide()
            lifecycleScope.launch {
                viewModel.loadProfile()
            }
        } else {
            binding.layoutLoggedIn.hide()
            binding.layoutLoggedOut.show()
        }
    }

    private fun loginWithKakao() {
        val context = requireContext()
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (view == null) return@loginWithKakaoTalk

                if (error != null) {
                    Log.e(TAG, "카카오톡 로그인 실패", error)
                    loginWithKakaoAccount(context)
                } else if (token != null) {
                    fetchKakaoProfile()
                }
            }
        } else {
            loginWithKakaoAccount(context)
        }
    }

    private fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (view == null) return@loginWithKakaoAccount

            if (error != null) {
                Log.e(TAG, "카카오계정 로그인 실패", error)
                Toast.makeText(context, R.string.error_network_generic, Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                fetchKakaoProfile()
            }
        }
    }

    private fun fetchKakaoProfile() {
        UserApiClient.instance.me { user, error ->
            if (view == null) return@me

            if (error != null) {
                Log.e(TAG, "카카오 사용자 정보 조회 실패", error)
                Toast.makeText(requireContext(), R.string.error_network_generic, Toast.LENGTH_SHORT).show()
            } else if (user != null) {
                val nickname = user.kakaoAccount?.profile?.nickname.orEmpty()
                val profileImageUrl = user.kakaoAccount?.profile?.profileImageUrl.orEmpty()
                lifecycleScope.launch {
                    viewModel.saveProfile(nickname, profileImageUrl)
                }
            }
            refreshLoginState()
        }
    }

    private fun logout() {
        UserApiClient.instance.logout { error ->
            if (view == null) return@logout

            if (error != null) {
                Log.e(TAG, "카카오 로그아웃 실패", error)
            }
            lifecycleScope.launch {
                viewModel.clearProfile()
                refreshLoginState()
            }
        }
    }
}
