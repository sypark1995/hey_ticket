package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sypark.data.db.entity.Genre
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.model.MainViewModel
import com.sypark.openTicket.view.adapter.RankingFilterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val TAG = "MainFragment"

    private val viewModel: MainViewModel by viewModels()
    private lateinit var rankingAdapter: RankingFilterAdapter
    private val rankingList = listOf(
        Genre("AAAA", "전체"),
        Genre("AAAA", "연극"),
        Genre("BBBC", "무용(서양/한국무용)"),
        Genre("BBBE", "대중무용"),
        Genre("CCCA", "클래식(서양음악)"),
        Genre("CCCC", "국악(한국음악)"),
        Genre("CCCD", "대중음악"),
        Genre("EEEA", "복합"),
        Genre("EEEB", "서커스/마술"),
        Genre("GGGA", "뮤지컬")
    )

    override fun init(view: View) {
        binding.layoutBottom.navigationBottom.menu.getItem(0).isChecked = true

//        binding.textRecommend.setOnClickListener {
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToRecommendFragment())
//        }

        binding.topTitle.imgSearch.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
        }

        binding.recyclerviewRankingFilter.apply {
            rankingAdapter = RankingFilterAdapter { position, item ->
                Log.e("!!!!", item.toString())
                onItemClicked(position)
            }

            rankingAdapter.submitList(rankingList)
            adapter = rankingAdapter
            rankingAdapter.setSelectedPosition(0)
        }

//        binding.openKindRecyclerview.adapter.apply {
//
//        }
//        val pageMarginPx = resources.getDimensionPixelOffset()
        lifecycleScope.launch {
//            viewModel.getHitsMelonData()
//            viewModel.melonList.value?.let {
//                binding.viewpager.adapter = InfiniteAdapter(it)
//            }
//
//            binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//            binding.viewpager.setCurrentItem(currentPosition, false)
//
//            binding.viewpager.apply {

//                offscreenPageLimit = 3
//                clipChildren = false
//                clipToPadding = false
//
//                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//
//                setPageTransformer(
//                    CompositePageTransformer().apply {
//                        addTransformer(MarginPageTransformer(1))
//                        addTransformer { view: View, fl: Float ->
//                            val v = 1 - abs(fl)
//                            view.scaleY = 0.85f + v * 0.15f
//                        }
//                    }
//                )

//                viewModel.melonList.value.apply {
//                    adapter = InfiniteAdapter(this!!)
//                }
//                setCurrentItem(Int.MAX_VALUE / 2, false)

//                adapter = viewModel.melonList.value?.let {
//                    ViewPagerAdapter(it)
//                }

//                registerOnPageChangeCallback(object : OnPageChangeCallback() {
//                    override fun onPageSelected(position: Int) {
//                        super.onPageSelected(position)
//
//                        binding.layoutViewpager.apply {
//                            Glide.with(this)
//                                .load(viewModel.melonList.value!![position].image_url)
//                                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
//                                .into(object : CustomTarget<Drawable>() {
//                                    override fun onResourceReady(
//                                        resource: Drawable,
//                                        transition: Transition<in Drawable>?
//                                    ) {
//                                        binding.layoutViewpager.background = resource
//                                    }
//
//                                    override fun onLoadCleared(placeholder: Drawable?) {}
//
//                                })
//                        }
//                    }
//
//                    override fun onPageScrollStateChanged(state: Int) {
//                        super.onPageScrollStateChanged(state)
//                    }
//                })
//            }
        }

//        binding.kindRecyclerview.run {
//
//            layoutManager = LinearLayoutManager(view.context).apply {
//                orientation = RecyclerView.HORIZONTAL
//            }
//
//            adapter = SortTicketAdapter().apply {
//                setListInfo(
//                    arrayListOf(
//                        getString(R.string.concert),
//                        getString(R.string.drama),
//                        getString(R.string.musical)
//                    )
//                )
//                setTicketClickListener(this@MainFragment)
//            }
//        }

//        binding.btn.setOnClickListener {
//            Log.e("!!!", "click")
//        }
    }

    private fun onItemClicked(position: Int) {
        rankingAdapter.setSelectedPosition(position)
    }
}