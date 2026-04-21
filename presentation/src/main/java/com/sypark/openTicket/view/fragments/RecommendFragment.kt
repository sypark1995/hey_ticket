package com.sypark.openTicket.view.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecommendBinding
import com.sypark.openTicket.model.ActivityViewModel
import com.sypark.openTicket.popups.showClosePopup
import com.sypark.openTicket.view.adapter.RecommendAreaAdapter
import com.sypark.openTicket.view.adapter.RecommendGenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {

    private lateinit var recommendAreaAdapter: RecommendAreaAdapter
    private lateinit var recommendGenreAdapter: RecommendGenreAdapter

    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun init(view: View) {

        // todo_sypark https://salix97.tistory.com/268 참고
        FlexboxLayoutManager(view.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
        }.let {
            binding.recyclerviewArea.apply {
                layoutManager = it
                recommendAreaAdapter = RecommendAreaAdapter {
                    activityViewModel.setAreas(it)
                }

                recommendAreaAdapter.submitList(Common.areaList)
                adapter = recommendAreaAdapter
            }


        }

        FlexboxLayoutManager(view.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
        }.let {
            binding.recyclerviewGenre.apply {
                layoutManager = it
                recommendGenreAdapter = RecommendGenreAdapter {
                    activityViewModel.setGenres(it)
                }

                recommendGenreAdapter.submitList(Common.registerRecommendList)
                adapter = recommendGenreAdapter
            }
        }

        binding.apply {
            layoutRegisterTop.imgBack.setOnClickListener {
                backPressed()
            }

            layoutRegisterTop.imgClose.setOnClickListener {
                backPressed()
            }

            btnNext.setOnClickListener {
                findNavController().navigate(
                    RecommendFragmentDirections.actionRecommendFragmentToRecommendKeywordFragment()
                )
            }
        }
    }

    override fun backPressed() {
        requireActivity().showClosePopup(getString(R.string.register_close_popup), {

        }, {
            findNavController().popBackStack()
        })
    }
}
