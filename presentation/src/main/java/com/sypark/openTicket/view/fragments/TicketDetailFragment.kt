package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.sypark.data.db.entity.PlaceDetail
import com.sypark.data.db.entity.TicketDetail
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
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TicketDetailFragment :
    BaseFragment<FragmentTicketDetailBinding>(R.layout.fragment_ticket_detail) {

    private val args by navArgs<TicketDetailFragmentArgs>()
    private val viewModel: TicketDetailViewModel by viewModels()
    private var naverMap: NaverMap? = null
    private lateinit var bitmap: Bitmap

    //todo_sypark 네트워크 핸들링 예정
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun init(view: View) {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.layout_information_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.layout_information_map, it).commit()
            }

        mapFragment.getMapAsync {
            naverMap = it
        }

        lifecycleScope.launch {
            viewModel.getTicketDetailData((args.item))
        }

        viewModel.ticketDetail.observe(viewLifecycleOwner) { item ->
            binding.apply {
                when (Common.compareDate(item.startDate, item.endDate)) {
                    Common.DateType.BEFORE -> {
                        val startDate =
                            Date(SimpleDateFormat("yyyy-MM-dd").parse(item.startDate)!!.time).time

                        val nowDate = SimpleDateFormat("yyyy-MM-dd").parse(
                            SimpleDateFormat("yyyy-MM-dd").format(
                                Date(System.currentTimeMillis())
                            )
                        )

                        try {
                            textState.setBackgroundResource(R.drawable.round_4_blue)
                            textState.setTextColor(
                                ContextCompat.getColor(binding.root.context, R.color.blue_2C70F2)
                            )
                            textState.text =
                                "D-${(startDate - nowDate.time) / (24 * 60 * 60 * 1000)}"
                        } catch (e: Exception) {
                            textState.text = ""
                        }
                    }

                    Common.DateType.START -> {
                        textState.setTextColor(
                            ContextCompat.getColor(binding.root.context, R.color.green_2C70F2)
                        )

                        textState.setBackgroundResource(R.drawable.round_4_green)
                        textState.text = "공연 중"
                    }

                    Common.DateType.FINISH -> {
                        textState.setBackgroundResource(R.drawable.round_4_gray)
                        textState.text = "종료"
                    }

                    Common.DateType.ERROR -> {
                        textState.visibility = View.GONE
                    }
                }

                Common.genreList.single {
                    it.code == item.genre
                }.apply {
                    ticketGenre.text = genrenm
                }

                textTitle.text = item.title

                Glide.with(view.context)
                    .load(item.poster)
                    .error(R.drawable.icon_default_poster)
                    .into(imgPoster)

                if ((item.startDate.isEmpty() || item.endDate.isEmpty())) {
                    layoutInformationDate.visibility = View.GONE
                } else {
                    textInformationDate.text =
                        "${item.startDate}${Common.getDayOfWeek(item.startDate)} ~ ${item.endDate}${
                            Common.getDayOfWeek(item.endDate)
                        }"
                    textDate.text =
                        "${item.startDate}${Common.getDayOfWeek(item.startDate)} ~ ${item.endDate}${
                            Common.getDayOfWeek(item.endDate)
                        }"
                }

                textInformationPeriod.text = item.schedule

                item.cast.let {
                    if (it.isNullOrEmpty()) {
                        layoutInformationCast.visibility = View.GONE
                    } else {
                        textInformationCast.text = it
                    }
                }

                item.price.let {
                    if (it.isNullOrEmpty()) {
                        layoutInformationPrice.visibility = View.GONE
                    } else {
                        textInformationPrice.text = it
                    }
                }

                if (item.theater.isEmpty()) {
                    layoutInformationPlace.visibility = View.GONE
                } else {
                    textInformationPlace.text = item.theater
                }

                item.company.let {
                    if (it.isNullOrEmpty()) {
                        layoutInformationHost.visibility = View.GONE
                    } else {
                        textInformationHost.text = it
                    }
                }

                item.age.let {
                    if (it.isNullOrEmpty()) {
                        layoutInformationAge.visibility = View.GONE
                    } else {
                        textInformationAge.text = it
                    }
                }

                item.crew.let {
                    if (it.isNullOrEmpty()) {
                        layoutInformationEtcCrew.visibility = View.GONE
                    } else {
                        textInformationEtcCrew.text = it
                    }
                }

                if (item.storyUrls.isEmpty()) {
                    layoutInformationDetail.visibility = View.GONE
                } else {
                    val bitmapList = mutableListOf<Bitmap>()
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {

                        item.storyUrls.forEachIndexed { index, detail ->
                            bitmapList.add(
                                index,
                                BaseUtil.convertBitmapFromURL(
                                    detail,
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

                    btnInformationDetail.visibility = View.GONE
                    imgInformationDetail.visibility = View.GONE
                    imgInformationDetailFull.visibility = View.VISIBLE
                }

                setNaverMarker(view.context, item.address, item.latitude, item.longitude)

                if (item.phoneNumber.trim().isEmpty()) {
                    layoutInformationInquiry.visibility = View.GONE
                } else {
                    textInformationInquiry.text = item.phoneNumber
                    textInformationInquiry.setOnClickListener {
                        Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:" + item.phoneNumber)
                            startActivity(this)
                        }
                    }
                }

                if (item.address.isEmpty()) {
                    layoutInformationPlace.visibility = View.GONE
                } else {
                    textInformationLatLng.text = item.address
                }

                btnWebView.setOnClickListener {
                    findNavController().navigate(
                        TicketDetailFragmentDirections.actionTicketDetailFragmentToWebViewFragment(
                            item.title
                        )
                    )
                }

                imgTopButton.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.Main) {
                        scrollView.fullScroll(ScrollView.FOCUS_UP)
                    }
                }

                imgClose.setOnClickListener {
                    findNavController().popBackStack()
                }

                imgShare.setOnClickListener {
                    isKakaoInstall(it.context, item)
                }

                scrollView.viewTreeObserver.addOnScrollChangedListener {
                    viewModel.setScrollYPosition(scrollView.scrollY)
                }
            }
        }

        viewModel.scrollYPosition.observe(viewLifecycleOwner) {
            if (it > 0) {
                binding.imgTopButton.visibility = View.VISIBLE
            } else {
                binding.imgTopButton.visibility = View.GONE
            }
        }
    }

    private fun setNaverMarker(
        context: Context,
        address: String,
        latitude: Double,
        longitude: Double
    ) {
        Marker().apply {
            position = LatLng(latitude, longitude)
            width = 50
            height = 80
            iconTintColor = Color.rgb(0, 0, 0)
            naverMap.let {

                it?.moveCamera(CameraUpdate.scrollTo(position))
                it?.uiSettings?.apply {
                    isScaleBarEnabled = false
                    isZoomControlEnabled = false
                    isScrollGesturesEnabled = false
                    isZoomGesturesEnabled = false
                    isTiltGesturesEnabled = false
                    isRotateGesturesEnabled = false
                    isStopGesturesEnabled = false
                }
                map = it
            }
        }

        val encodeAddress = URLEncoder.encode(address, "UTF-8")
        naverMap?.setOnMapClickListener { _, _ ->
            openExternalApp(
                context, latitude, longitude,
                encodeAddress
            )
        }
    }

    private fun kakaoShare(
        ticketDetail: TicketDetail
    ): LocationTemplate {
        return LocationTemplate(
            address = ticketDetail.address,
            addressTitle = "공연장",
            content = Content(
                title = ticketDetail.title,
                imageUrl = ticketDetail.poster.let {
                    if (it.isNullOrEmpty()) {
                        ""
                    } else {
                        it
                    }
                },
                link = Link(
                    webUrl = "https://developers.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            social = null
        )
    }

    private fun kakaoShare2(
        ticketDetail: TicketDetail,
        placeItem: PlaceDetail?
    ): FeedTemplate {
        Log.e("address", URLEncoder.encode(placeItem?.address, "UTF-8"))
        return FeedTemplate(
            content = Content(
                title = ticketDetail.title,
                imageUrl = ticketDetail.poster.let {
                    if (it.isNullOrEmpty()) {
                        ""
                    } else {
                        it
                    }
                },
                link = Link(
                    webUrl = "https://developers.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            buttons = listOf(
                Button(
                    title = getString(R.string.kakao_share_performance_information),
                    Link(
                        androidExecutionParams = mapOf(

                        ),
                        webUrl = null,
                        mobileWebUrl = null
                    )
                ),
                Button(
                    title = getString(R.string.kakao_share_location),
                    Link(
                        androidExecutionParams = mapOf(),
//                        webUrl = "nmap://place?lat=${placeItem?.latitude}&lng=${placeItem?.longitude}&name=${placeItem?.address}&appname=com.sypark.openTicket",
//                        mobileWebUrl = "nmap://place?lat=${placeItem?.latitude}&lng=${placeItem?.longitude}&name=${placeItem?.address}&appname=com.sypark.openTicket"
                    )
                )
            )
        )
    }

    private fun isKakaoInstall(
        context: Context,
        ticketDetail: TicketDetail,
    ) {
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(
                context,
                kakaoShare(ticketDetail)
            ) { sharingResult, error ->
                if (error != null) {
                    Log.e("TAG", "카카오톡 공유 실패", error)
                } else if (sharingResult != null) {
                    Log.e("TAG", "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.e("TAG", "Warning Msg: ${sharingResult.warningMsg}")
                    Log.e("TAG", "Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else {
            Toast.makeText(
                context,
                resources.getString(R.string.kakao_not_install),
                Toast.LENGTH_SHORT
            )
        }
    }

    private fun openExternalApp(context: Context, lat: Double, lng: Double, address: String) {
        // "nmap://actionPath?lat={$lat}&lng={$lng}&appname={com.sypark.openTicket}"
//        val url =
//            "nmap://route/public?dlat=$lat&dlng=$lng&dname=$address&appname=com.example.myapp"        //   대중교통 길찾기
        val url =
            "nmap://place?lat=$lat&lng=$lng&name=$address&appname=com.sypark.openTicket"            //   마커 url
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