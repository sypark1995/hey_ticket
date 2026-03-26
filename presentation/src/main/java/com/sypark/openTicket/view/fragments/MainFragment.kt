package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.model.MainViewModel
import com.sypark.openTicket.view.adapter.GenreAdapter
import com.sypark.openTicket.view.adapter.MainDefaultAdapter
import com.sypark.openTicket.view.adapter.RankingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val TAG = "MainFragment"

    private val viewModel: MainViewModel by viewModels()

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var newFilterAdapter: GenreAdapter

    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var newTicketAdapter: MainDefaultAdapter
    private lateinit var etcTicketAdapter: MainDefaultAdapter
    private lateinit var campusTicketAdapter: MainDefaultAdapter

    override fun init(view: View) {
        binding.layoutBottom.navigationBottom.menu.getItem(0).isChecked = true

        binding.topTitle.imgSearch.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
        }

        binding.layoutRecommand.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFirstFragment())
        }

        binding.recyclerviewRankingFilter.apply {
            genreAdapter = GenreAdapter { position, item ->
                rankingFilterItemClicked(position)

                lifecycleScope.launch {
                    viewModel.getRankingData(item.code)
                }
            }

            genreAdapter.submitList(Common.genreList)
            adapter = genreAdapter
            genreAdapter.setSelectedPosition(0)
        }

        binding.recyclerviewRanking.apply {
            rankingAdapter = RankingAdapter {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToTicketDetailFragment(it))
            }.apply {
                lifecycleScope.launch {
                    viewModel.getRankingData("")
                }
            }
            adapter = rankingAdapter
        }

        viewModel.rankingList.observe(viewLifecycleOwner) {
            rankingAdapter.submitList(it)
        }

        viewModel.isShimmerLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.shimmer.hideShimmer()
            } else {
                binding.shimmer.showShimmer(true)
            }
        }

        binding.recyclerviewNewTicketFilter.apply {
            newFilterAdapter = GenreAdapter { position, item ->
                Log.e("newFilterAdapter", item.toString())
                newFilterItemClicked(position)
                lifecycleScope.launch {
                    viewModel.getNewTicketData(item.code)
                }
            }

            newFilterAdapter.submitList(Common.genreList)
            adapter = newFilterAdapter
            newFilterAdapter.setSelectedPosition(0)
        }

        binding.recyclerviewNewTicket.apply {
            newTicketAdapter = MainDefaultAdapter {

            }

            lifecycleScope.launch {
                viewModel.getNewTicketData("")
            }
            adapter = newTicketAdapter
        }

        viewModel.newTicketList.observe(this) {
            newTicketAdapter.submitList(it)
        }

        binding.recyclerviewCampusTicket.apply {
            campusTicketAdapter = MainDefaultAdapter {

            }
//            campusTicketAdapter.submitList()
            adapter = campusTicketAdapter
        }

        binding.recyclerviewEtcTicket.apply {
            etcTicketAdapter = MainDefaultAdapter {

            }
//            etcTicketAdapter.submitList()
            adapter = etcTicketAdapter
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

    private fun rankingFilterItemClicked(position: Int) {
        genreAdapter.setSelectedPosition(position)
    }

    private fun newFilterItemClicked(position: Int) {
        newFilterAdapter.setSelectedPosition(position)
    }
}