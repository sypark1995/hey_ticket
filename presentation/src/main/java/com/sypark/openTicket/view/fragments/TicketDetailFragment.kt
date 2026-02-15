package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.sypark.data.db.entity.Ticket
import com.sypark.openTicket.BaseUtil
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentTicketDetailBinding
import com.sypark.openTicket.model.TicketDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder

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

        viewModel.ticketDetail.observe(this) { item ->
            if (item == null) {

            } else {
                binding.apply {
                    textTitle.text = item.title

                    Glide.with(view.context)
                        .load(item.poster).into(imgPoster)

                    if ((item.startDate.isEmpty() || item.endDate.isEmpty())) {
                        layoutInformationDate.visibility = View.GONE
                    } else {
                        textInformationDate.text = "${item.startDate}${Common.getDayOfWeek(item.startDate)} ~ ${item.endDate}${Common.getDayOfWeek(item.endDate)}"
                        textDate.text = "${item.startDate}${Common.getDayOfWeek(item.startDate)} ~ ${item.endDate}${Common.getDayOfWeek(item.endDate)}"
                    }

                    if (item.cast.trim().isEmpty()) {
                        layoutInformationCast.visibility = View.GONE
                    } else {
                        textInformationCast.text = item.cast
                    }

                    if (item.pcseguidance.isEmpty()) {
                        layoutInformationPrice.visibility = View.GONE
                    } else {
                        textInformationPrice.text = item.pcseguidance
                    }

                    if (item.place.isEmpty()) {
                        layoutInformationPlace.visibility = View.GONE
                    } else {
                        textInformationPlace.text = item.place
                    }

                    if (item.styurls.isEmpty()) {
                        layoutInformationDetail.visibility = View.GONE
                    } else {
                        //todo_sypark 유섭이한테 이미지 어떻게 뿌렸는지 확인 예정
                    }

                    if (item.entrpsnm.trim().isEmpty()) {
                        layoutInformationHost.visibility = View.GONE
                    } else {
                        textInformationHost.text = item.entrpsnm
                    }

                    if (item.age.isEmpty()) {
                        layoutInformationAge.visibility = View.GONE
                    } else {
                        textInformationAge.text = item.age
                    }

                    if (item.crew.trim().isEmpty()) {
                        layoutInformationEtcCrew.visibility = View.GONE
                    } else {
                        textInformationEtcCrew.text = item.crew
                    }

                    if (item.styurls.isEmpty()) {
                        layoutInformationDetail.visibility = View.GONE
                    } else {
                        val bitmapList = mutableListOf<Bitmap>()
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

                            item.styurls.forEachIndexed { index, detail ->
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

                    btnWebView.setOnClickListener {
                        findNavController().navigate(
                            TicketDetailFragmentDirections.actionTicketDetailFragmentToWebViewFragment(
                                item.title
                            )
                        )
                    }

                    binding.imgClose.setOnClickListener {
                        findNavController().popBackStack()
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
                    naverMap.uiSettings.apply {
                        isScaleBarEnabled = false
                        isZoomControlEnabled = false
                        isScrollGesturesEnabled = false
                        isZoomGesturesEnabled = false
                        isTiltGesturesEnabled = false
                        isRotateGesturesEnabled = false
                        isStopGesturesEnabled = false
                    }
                    map = naverMap
                }
                val encodeAddress = URLEncoder.encode(it.address, "UTF-8")
                naverMap.setOnMapClickListener { pointF, latLng ->
                    openExternalApp(
                        view.context, it.latitude, it.longitude,
                        encodeAddress
                    )
                }
            }
        }
    }

    private fun openExternalApp(context: Context, lat: Double, lng: Double, address: String) {
        // "nmap://actionPath?lat={$lat}&lng={$lng}&appname={com.sypark.openTicket}"
//        val url =
//            "nmap://route/public?dlat=$lat&dlng=$lng&dname=$address&appname=com.example.myapp"        //   대중교통 길찾기
        val url = "nmap://place?lat=$lat&lng=$lng&name=$address&appname=com.sypark.openTicket"            //   마커 url
//        val url = "nmap://actionPath?parameter=value&appname={com.sypark.openTicket}"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        if (isInstalledExternalApp(context, "com.nhn.android.nmap")) {
            context.startActivity(intent)
        } else {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.nhn.android.nmap")
                )
            )
        }
    }


    /**default 앱 to 앱
     * */
    private fun openExternalApp(context: Context, intent: Intent) {
        if (isInstalledExternalApp(context, "com.nhn.android.nmap")) {
            context.startActivity(intent)
        } else {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.nhn.android.nmap")
                )
            )
        }
    }

    private fun isInstalledExternalApp(context: Context, packageName: String): Boolean {
        var isInstalled = false
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val packageManager = context.packageManager
        val installedApps = packageManager.queryIntentActivities(mainIntent, 0)
        for (resolveInfo in installedApps) {
            if (resolveInfo.activityInfo.packageName.contains(packageName)) {
                isInstalled = true
                break
            }
        }
        return isInstalled
    }
}