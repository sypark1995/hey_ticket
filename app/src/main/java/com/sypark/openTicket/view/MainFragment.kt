package com.sypark.openTicket.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.model.MelonTicket

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    TicketClickListener {
    private val TAG = "MainFragment"

    override fun init(view: View) {

//        {
//            "id": 12,
//            "image_url": "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2016/04/2016042416055341773396-8a2f-4e4e-8563-5f2a59c94937.jpg/melon/",
//            "title": "2016 XIA 5th ASIA TOUR CONCERT in SEOUL 1차 티켓 오픈 안내",
//            "ticket_opening_date": "2016.04.29(금) 20:00",
//            "registration_date": "2016.04.24",
//            "hits": 40
//        }
        val melonData = ArrayList<MelonTicket>().let {
            it.apply {
                add(
                    MelonTicket(
                        12,
                        "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2016/04/2016042416055341773396-8a2f-4e4e-8563-5f2a59c94937.jpg/melon/",
                        "2016 XIA 5th ASIA TOUR CONCERT in SEOUL 1차 티켓 오픈 안내",
                        "40",
                        "",
                        ""
                    )
                )

                add(
                    MelonTicket(
                        12,
                        "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2016/04/2016042416055341773396-8a2f-4e4e-8563-5f2a59c94937.jpg/melon/",
                        "2016 XIA 5th ASIA TOUR CONCERT in SEOUL 1차 티켓 오픈 안내",
                        "40",
                        "",
                        ""
                    )
                )

                add(
                    MelonTicket(
                        12,
                        "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2016/04/2016042416055341773396-8a2f-4e4e-8563-5f2a59c94937.jpg/melon/",
                        "2016 XIA 5th ASIA TOUR CONCERT in SEOUL 1차 티켓 오픈 안내",
                        "40",
                        "",
                        ""
                    )
                )
            }
        }


//        val pageMarginPx = resources.getDimensionPixelOffset()

        binding.viewpager.apply {
            this.adapter = ViewPagerAdapter(melonData)
            this.offscreenPageLimit = 3
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