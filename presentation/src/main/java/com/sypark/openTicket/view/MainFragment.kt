package com.sypark.openTicket.view

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sypark.domain.model.OpenTicket
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlin.math.abs

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    TicketClickListener {
    private val TAG = "MainFragment"

    private val viewModel: MainViewModel by viewModels()

    override fun init(view: View) {

//        {
//            "id": 12,
//            "image_url": "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2016/04/2016042416055341773396-8a2f-4e4e-8563-5f2a59c94937.jpg/melon/",
//            "title": "2016 XIA 5th ASIA TOUR CONCERT in SEOUL 1차 티켓 오픈 안내",
//            "ticket_opening_date": "2016.04.29(금) 20:00",
//            "registration_date": "2016.04.24",
//            "hits": 40
//        }
        val melonData = ArrayList<OpenTicket>().let {
            it.apply {
                add(
                    OpenTicket(
                        12,
                        "2016 XIA 5th ASIA TOUR CONCERT in SEOUL 1차 티켓 오픈 안내",
                        "2016.04.29(금) 20:00",
                        "40",
                        "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2016/04/2016042416055341773396-8a2f-4e4e-8563-5f2a59c94937.jpg/melon/",
                    )
                )

                add(
                    OpenTicket(
                        12,
                        "2022 이지혜 단독 팬콘서트 〈첫번졔〉 티켓 오픈 안내",
                        "2016.04.29(금) 20:00",
                        "40",
                        "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2022/12/202212081028396e28ec63-adab-492e-9bcf-d0c64cef6215.jpg/melon/",
                    )
                )

                add(
                    OpenTicket(
                        12,
                        "2016 XIA 5th ASIA TOUR CONCERT in SEOUL 1차 티켓 오픈 안내",
                        "2016.04.29(금) 20:00",
                        "40",
                        "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2022/11/20221129114816d5db630b-419a-4388-b5dd-204ad640523d.jpg/melon/",
                    )
                )
            }
        }


//        val pageMarginPx = resources.getDimensionPixelOffset()

        binding.viewpager.apply {
            this.adapter = ViewPagerAdapter(melonData)
            this.offscreenPageLimit = 3

            setPageTransformer(
                CompositePageTransformer().apply {
                    addTransformer(MarginPageTransformer(1))
                    addTransformer { view: View, fl: Float ->
                        val v = 1 - abs(fl)
                        view.scaleY = 0.8f + v * 0.2f
                    }
                }
            )

            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.e(TAG, position.toString())
                    viewModel.setBackgroundPosition(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })
        }

        viewModel.backgroundPos.observe(this, Observer {
            Log.e(TAG, "observe")

            binding.layoutViewpager.apply {
                Glide.with(this)
                    .load(melonData[it].image_url)
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
            viewModel.getData()
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