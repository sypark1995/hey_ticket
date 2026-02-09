package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.sypark.data.db.entity.Ticket
import com.sypark.openTicket.BaseUtil
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentTicketDetailBinding
import com.sypark.openTicket.model.TicketDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class TicketDetailFragment :
    BaseFragment<FragmentTicketDetailBinding>(R.layout.fragment_ticket_detail) {

    private val args by navArgs<TicketDetailFragmentArgs>()
    private val viewModel: TicketDetailViewModel by viewModels()
    private lateinit var naverMap: NaverMap
    private lateinit var bitmap: Bitmap

    @SuppressLint("SetTextI18n")
    override fun init(view: View) {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.layout_information_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.layout_information_map, it).commit()
            }

        mapFragment.getMapAsync {
            naverMap = it
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.getTicketDetailData((args.item as Ticket).mt20id)
            viewModel.getPlaceDetailData((args.item as Ticket).mt10id)
        }

        viewModel.ticketDetail.observe(this) {
            if (it == null) {

            } else {
                binding.apply {
                    textTitle.text = it.title

                    Glide.with(view.context)
                        .load(it.poster).into(imgPoster)

                    if ((it.startDate.isEmpty() || it.endDate.isEmpty())) {
                        layoutInformationDate.visibility = View.GONE
                    } else {
                        textInformationDate.text = "${it.startDate} ~ ${it.endDate}"
                        textDate.text = "${it.startDate} ~ ${it.endDate}"
                    }

                    if (it.cast.trim().isEmpty()) {
                        layoutInformationCast.visibility = View.GONE
                    } else {
                        textInformationCast.text = it.cast
                    }

                    if (it.pcseguidance.isEmpty()) {
                        layoutInformationPrice.visibility = View.GONE
                    } else {
                        textInformationPrice.text = it.pcseguidance
                    }

                    if (it.place.isEmpty()) {
                        layoutInformationPlace.visibility = View.GONE
                    } else {
                        textInformationPlace.text = it.place
                    }

                    if (it.styurls.isEmpty()) {
                        layoutInformationDetail.visibility = View.GONE
                    } else {
                        //todo_sypark 유섭이한테 이미지 어떻게 뿌렸는지 확인 예정
                    }

                    if (it.entrpsnm.trim().isEmpty()) {
                        layoutInformationHost.visibility = View.GONE
                    } else {
                        textInformationHost.text = it.entrpsnm
                    }

                    if (it.age.isEmpty()) {
                        layoutInformationAge.visibility = View.GONE
                    } else {
                        textInformationAge.text = it.age
                    }

                    if (it.crew.trim().isEmpty()) {
                        layoutInformationEtcCrew.visibility = View.GONE
                    } else {
                        textInformationEtcCrew.text = it.crew
                    }

                    if (it.styurls.isEmpty()) {
                        layoutInformationDetail.visibility = View.GONE
                    } else {
                        val bitmapList = mutableListOf<Bitmap>()
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

                            it.styurls.forEachIndexed { index, detail ->
                                bitmapList.add(
                                    index,
                                    BaseUtil.convertBitmapFromURL(
                                        detail.url,
                                        imgInformationDetail.width,
                                        imgInformationDetail.height
                                    )!!
                                )
                            }

                            // todo_sypark 이미지 2개로 처리하는거 이상함;; 나중에 처리예정
                            val mergeBitmap = BaseUtil.mergeBitmapsVertical(
                                bitmapList
                            )

                            val cropBitmap =
                                if (mergeBitmap.height <= imgInformationDetail.height) {
                                    Bitmap.createBitmap(
                                        mergeBitmap,
                                        0,
                                        0,
                                        mergeBitmap.width,
                                        mergeBitmap.height
                                    )
                                } else {
                                    Bitmap.createBitmap(
                                        mergeBitmap,
                                        0,
                                        0,
                                        mergeBitmap.width,
                                        imgInformationDetail.height
                                    )
                                }

                            withContext(Dispatchers.Main) {

                                if (mergeBitmap.height <= imgInformationDetail.height) {
                                    btnInformationDetail.visibility = View.GONE
                                }
                                imgInformationDetailFull.setImageBitmap(mergeBitmap)
                                imgInformationDetail.setImageBitmap(cropBitmap)
                            }
                        }

                    }


                    btnInformationDetail.setOnClickListener {
                        //todo_sypark viewmodel로 처리 예정
                        if (imgInformationDetail.visibility == View.VISIBLE) {
                            btnInformationDetail.text =
                                resources.getString(R.string.ticket_detail_information_detail_close)
                            imgInformationDetail.visibility = View.GONE
                            imgInformationDetailFull.visibility = View.VISIBLE
                        } else {
                            btnInformationDetail.text =
                                resources.getString(R.string.ticket_detail_information_detail_more)
                            imgInformationDetail.visibility = View.VISIBLE
                            imgInformationDetailFull.visibility = View.GONE
                        }

                    }
                }
            }
        }

        viewModel.placeDetail.observe(this) {
            binding.apply {
                if (it.address.isEmpty()) {
                    layoutInformationPlace.visibility = View.GONE
                } else {
                    textInformationLatLng.text = it.address
                }

                if (it.phoneNumber.isEmpty()) {
                    layoutInformationInquiry.visibility = View.GONE
                } else {
                    textInformationInquiry.text = it.phoneNumber
                }

                Marker().apply {
                    position = LatLng(it.latitude, it.longitude)
                    width = 50
                    height = 80
                    iconTintColor = Color.rgb(0, 0, 0)
                    naverMap.moveCamera(CameraUpdate.scrollTo(position))
                    map = naverMap
                }
            }
        }
    }
}