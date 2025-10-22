package com.sypark.openTicket.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    TicketClickListener {
    private val TAG = "MainFragment"

    // [
    //    {
    //        "id": 6819,
    //        "imageUrl": "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2022/12/202212081028396e28ec63-adab-492e-9bcf-d0c64cef6215.jpg/melon/",
    //        "title": "2022 이지혜 단독 팬콘서트 〈첫번졔〉 티켓 오픈 안내",
    //        "date": "",
    //        "hits": 537
    //    },
    //    {
    //        "id": 6780,
    //        "imageUrl": "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2022/11/20221129114816d5db630b-419a-4388-b5dd-204ad640523d.jpg/melon/",
    //        "title": "먼데이프로젝트 시즌5 청춘의 밤［은종 연말 단독 콘서트］티켓 오픈 안내",
    //        "date": "2022.12.09(금) 20:00",
    //        "hits": 93
    //    },
    //    {
    //        "id": 6813,
    //        "imageUrl": "https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2022/12/202212071159251851be4c-f256-4f17-ba2f-97575522d9ff.jpg/melon/",
    //        "title": "파란노을 단독공연 ~ After the Night ~ 티켓 오픈 안내",
    //        "date": "2022.12.14(수) 18:00",
    //        "hits": 261
    //    },
    override fun init(view: View) {
        binding.viewpager.offscreenPageLimit = 5

        binding.viewpager.apply {

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