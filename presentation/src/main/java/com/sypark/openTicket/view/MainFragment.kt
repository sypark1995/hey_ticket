package com.sypark.openTicket.view

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    TicketClickListener {
    private val TAG = "MainFragment"

    private val viewModel: MainViewModel by viewModels()

    override fun init(view: View) {
//        val pageMarginPx = resources.getDimensionPixelOffset()
        lifecycleScope.launch {
            viewModel.getHitsMelonData()

            binding.viewpager.apply {
                this.offscreenPageLimit = 3

                viewModel.melonList.observe(this@MainFragment) {
                    this.adapter = ViewPagerAdapter(it)
                }



                setPageTransformer(
                    CompositePageTransformer().apply {
                        addTransformer(MarginPageTransformer(1))
                        addTransformer { view: View, fl: Float ->
                            val v = 1 - abs(fl)
                            view.scaleY = 0.85f + v * 0.15f
                        }
                    }
                )

                registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Log.e(TAG, position.toString())
                        viewModel.getViewPagerPosition(position)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                    }
                })
            }

            viewModel.viewPagerPosition.observe(this@MainFragment, Observer {
                Log.e(TAG, "observe")

                binding.layoutViewpager.apply {
                    Glide.with(this)
                        .load(viewModel.melonList.value!![it].image_url)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                        .into(object : CustomTarget<Drawable>() {
                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                binding.layoutViewpager.background = resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}

                        })
                }
            })
        }

        binding.kindRecyclerview.run {

            layoutManager = LinearLayoutManager(view.context).apply {
                orientation = RecyclerView.HORIZONTAL
            }

            adapter = SortTicketAdapter().apply {
                setListInfo(
                    arrayListOf(
                        getString(R.string.concert),
                        getString(R.string.drama),
                        getString(R.string.musical)
                    )
                )
                setTicketClickListener(this@MainFragment)
            }
        }
        binding.btn.setOnClickListener {
            Log.e("!!!", "click")
        }
    }

    override fun onClick(string: String) {
        when (string) {
            getString(R.string.concert) -> {
                Log.e(TAG, "concert")

            }
            getString(R.string.drama) -> {
                Log.e(TAG, "drama")
            }
            getString(R.string.musical) -> {
                Log.e(TAG, "musical")
            }
        }
    }

}