package com.sypark.openTicket.view.fragments

import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.excensions.dpToPx
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.MainViewModel
import com.sypark.openTicket.popups.showClosePopup
import com.sypark.openTicket.util.UserPreferencesDataStore
import com.sypark.openTicket.view.adapter.GenreAdapter
import com.sypark.openTicket.view.adapter.MainDefaultAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val TAG = "MainFragment"

    private val viewModel: MainViewModel by viewModels()

    private lateinit var newFilterAdapter: GenreAdapter

    private lateinit var newTicketAdapter: MainDefaultAdapter
    private lateinit var etcTicketAdapter: MainDefaultAdapter
    private lateinit var campusTicketAdapter: MainDefaultAdapter
    private lateinit var closingSoonAdapter: MainDefaultAdapter
    private lateinit var recommendedTicketAdapter: MainDefaultAdapter
    private lateinit var favoritesTicketAdapter: MainDefaultAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init(view: View) {

        binding.apply {
            layoutBottom.navigationBottom.menu.getItem(0).isChecked = true

            topTitle.imgSearch.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
            }

            if (AuthApiClient.instance.hasToken()) {
                layoutRecommand.hide()
            } else {
                layoutRecommand.show()
            }

            layoutRecommand.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToRecommendCategoryFragment())
            }

            textRecommand.run {
                val string = view.context.getString(R.string.main_recommend_content)

                val ssb =
                    SpannableStringBuilder(string).apply {
                        setSpan(ForegroundColorSpan(view.context.getColor(R.color.gray_949494)), 0, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        setSpan(AbsoluteSizeSpan(14.dpToPx()), 0, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        setSpan(setTypeface(ResourcesCompat.getFont(view.context, R.font.pretendard_bold)), 0, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                        setSpan(ForegroundColorSpan(view.context.getColor(R.color.gray_55555)),14,string.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        setSpan(AbsoluteSizeSpan(18.dpToPx()),14,string.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        setSpan(setTypeface(ResourcesCompat.getFont(view.context, R.font.pretendard_bold)), 14, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }

                text = ssb
            }

            layoutNewTicketMore.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToPagingNewFragment())
            }

            recyclerviewNewTicketFilter.apply {
                newFilterAdapter = GenreAdapter { position, item ->
                    Log.e("newFilterAdapter", item.toString())
                    newFilterItemClicked(position)
                    lifecycleScope.launch {
                        viewModel.getNewTicketData(item.code)
                    }
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                newFilterAdapter.submitList(Common.genreList)
                adapter = newFilterAdapter
                newFilterAdapter.setSelectedPosition(0)
            }

            recyclerviewNewTicket.apply {
                newTicketAdapter = MainDefaultAdapter {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTicketDetailFragment(
                            it
                        )
                    )
                }

                lifecycleScope.launch {
                    viewModel.getNewTicketData("")
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = newTicketAdapter
            }

            recyclerviewCampusTicket.apply {
                campusTicketAdapter = MainDefaultAdapter {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTicketDetailFragment(it)
                    )
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = campusTicketAdapter

                lifecycleScope.launch {
                    viewModel.getMusicalTicketData()
                }
            }

            recyclerviewEtcTicket.apply {
                etcTicketAdapter = MainDefaultAdapter {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTicketDetailFragment(it)
                    )
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = etcTicketAdapter

                lifecycleScope.launch {
                    viewModel.getTheaterTicketData()
                }
            }

            layoutCampusTicketMore.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToPagingNewFragment())
            }

            layoutEtcTicketMore.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToPagingNewFragment())
            }

            recyclerviewClosingSoon.apply {
                closingSoonAdapter = MainDefaultAdapter {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTicketDetailFragment(it)
                    )
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = closingSoonAdapter

                lifecycleScope.launch {
                    viewModel.getClosingSoonData()
                }
            }

            layoutClosingSoonMore.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToPagingNewFragment())
            }

            recyclerviewRecommendedTicket.apply {
                recommendedTicketAdapter = MainDefaultAdapter {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTicketDetailFragment(it)
                    )
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = recommendedTicketAdapter

                lifecycleScope.launch {
                    viewModel.getRecommendedData()
                }
            }

            layoutRecommendedTicketMore.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToPagingNewFragment())
            }

            recyclerviewFavoritesTicket.apply {
                favoritesTicketAdapter = MainDefaultAdapter {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTicketDetailFragment(it)
                    )
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = favoritesTicketAdapter

                lifecycleScope.launch {
                    viewModel.getFavoritesData()
                }
            }

            layoutFavoritesTicketMore.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToFavoritesFragment())
            }
        }

        viewModel.newTicketList.observe(this) {
            newTicketAdapter.submitList(it)
        }

        viewModel.musicalTicketList.observe(this) {
            binding.layoutCampusTicket.show()
            campusTicketAdapter.submitList(it)
        }

        viewModel.theaterTicketList.observe(this) {
            binding.layoutEtcTicket.show()
            etcTicketAdapter.submitList(it)
        }

        viewModel.closingSoonList.observe(this) {
            binding.layoutClosingSoon.show()
            closingSoonAdapter.submitList(it)
        }

        viewModel.recommendedList.observe(this) {
            binding.layoutRecommendedTicket.show()
            recommendedTicketAdapter.submitList(it)
        }

        viewModel.favoritesList.observe(this) {
            binding.layoutFavoritesTicket.show()
            favoritesTicketAdapter.submitList(it)
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) {
            Toast.makeText(view.context, R.string.error_network_generic, Toast.LENGTH_SHORT).show()
        }
    }

    override fun backPressed() {
        requireActivity().showClosePopup(getString(R.string.finish_app), {

        }, {
            requireActivity().finish()
        })
    }

    private fun newFilterItemClicked(position: Int) {
        newFilterAdapter.setSelectedPosition(position)
    }

    @Inject
    lateinit var userPreferencesDataStore: UserPreferencesDataStore

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

    private fun loginWithKakaoAccount(context: android.content.Context) {
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
                    userPreferencesDataStore.setKakaoProfile(nickname, profileImageUrl)
                }
            }
            binding.layoutRecommand.hide()
        }
    }
}