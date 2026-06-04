package com.sypark.openTicket.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.sypark.openTicket.Common
import com.sypark.openTicket.Preferences
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryDetailBinding
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.CategoryDetailViewModel
import com.sypark.openTicket.view.CategoryFilterAreaAdapter
import com.sypark.openTicket.view.CategorySortAdapter
import com.sypark.openTicket.view.PagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.internal.StringUtil
import org.threeten.bp.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()
    private lateinit var pagingAdapter: PagingAdapter
    private lateinit var categorySortAdapter: CategorySortAdapter
    private lateinit var categoryFilterAreaAdapter: CategoryFilterAreaAdapter
    private val args by navArgs<CategoryDetailFragmentArgs>()

    override fun init(view: View) {

        binding.apply {
            viewModel = categoryDetailViewModel

            categoryDetailViewModel.filterType.observe(viewLifecycleOwner) {
                Log.e("!!!", it.name)
            }

            includeLayoutSort.sortRecyclerview.apply {
                layoutManager = LinearLayoutManager(view.context)
                categorySortAdapter = CategorySortAdapter { position ->
                    onItemClicked(position)
//                    Preferences.sortPosition = position
                }

                categorySortAdapter.submitList(Common.sortList)
                adapter = categorySortAdapter
                categorySortAdapter.setSelectedPosition(1)
            }

            chipArea.apply {
                setOnCloseIconClickListener {
                    setChipFalse(it.context, this, "지역")
                    initFilterArea(it.context)
                }
            }

            chipDay.apply {
                setOnCloseIconClickListener {
                    setChipFalse(it.context, this, "공연일")
                    initFilterDay()
                }
            }

            chipStatus.apply {
                setOnCloseIconClickListener {
                    categoryDetailViewModel.isChecked(CategoryDetailViewModel.Status.EMPTY)

                    setChipFalse(
                        it.context,
                        this,
                        getString(categoryDetailViewModel.status.value!!.res)
                    )
                }
            }

            chipPrice.apply {
                setOnCloseIconClickListener {
                    categoryDetailViewModel.setPriceType(CategoryDetailViewModel.PriceType.EMPTY)

                    setChipFalse(
                        it.context,
                        this,
                        getString(categoryDetailViewModel.priceType.value!!.res)
                    )
                }
            }
            includeLayoutFilter.recyclerviewArea.apply {
                layoutManager = LinearLayoutManager(view.context)
                categoryFilterAreaAdapter = CategoryFilterAreaAdapter()
                adapter = categoryFilterAreaAdapter
                categoryFilterAreaAdapter.submitList(Common.areaList)

                categoryFilterAreaAdapter.setOnItemClickListener {
                    if (categoryFilterAreaAdapter.selectedList().size == 0) {
                        includeLayoutFilter.textAreaAll.setTextColor(
                            ContextCompat.getColor(
                                this.context,
                                R.color.black
                            )
                        )
                        includeLayoutFilter.checkboxAreaAll.show()
                    } else {
                        includeLayoutFilter.textAreaAll.setTextColor(
                            ContextCompat.getColor(
                                this.context,
                                R.color.gray_B7B7B7
                            )
                        )
                        includeLayoutFilter.checkboxAreaAll.hide()
                    }

                    categoryDetailViewModel.setFilterAreaList(categoryFilterAreaAdapter.selectedList())
                }
            }

            includeLayoutFilter.performanceCalendarView.setOnDateChangedListener { widget, date, selected ->
                date.apply {
                    if (selected) {
                        val selectedDay = "${date.month}.${date.day}(${
                            date.date.dayOfWeek.getDisplayName(
                                TextStyle.NARROW,
                                Locale.KOREAN
                            )
                        })"
                        categoryDetailViewModel.setSelectedDay(selectedDay)
                    }
                }
            }
            //todo_sypark 이것도 분기처리 이상함...
            categoryDetailViewModel.statusList.observe(viewLifecycleOwner) {
                Log.e("statusList",it.toString())
                if (it.contains(CategoryDetailViewModel.Status.EMPTY)) {

                } else {

                }

                if (it.contains(CategoryDetailViewModel.Status.ONGOING)) {
                    checkBoxWatcher(true, includeLayoutFilter.textPerformancePlanned)
                } else {
                    checkBoxWatcher(false, includeLayoutFilter.textPerformancePlanned)
                }

                if (it.contains(CategoryDetailViewModel.Status.UPCOMING)) {
                    checkBoxWatcher(true, includeLayoutFilter.textPerformanceDuring)
                } else {
                    checkBoxWatcher(false, includeLayoutFilter.textPerformanceDuring)
                }

                if (it.contains(CategoryDetailViewModel.Status.COMPLETED)) {
                    checkBoxWatcher(true, includeLayoutFilter.textPerformanceFinish)
                } else {
                    checkBoxWatcher(false, includeLayoutFilter.textPerformanceFinish)
                }
            }

            includeLayoutFilter.textAreaAll.setOnClickListener {
                initFilterArea(it.context)
            }

            categoryDetailViewModel.filterBtnType.observe(viewLifecycleOwner, ::filterViewWatcher)
        }
        /*//        binding.apply {
        //            imgBack.setOnClickListener {
        //                findNavController().popBackStack()
        //                Preferences.sortPosition = 1
        //            }
        //
        //            sortLayout.setOnClickListener {
        //                categoryDetailViewModel.setDetailSortLayoutVisibility(true)
        //            }
        //
        //            includeLayoutSort.imgClose.setOnClickListener {
        //                categoryDetailViewModel.setDetailSortLayoutVisibility(false)
        //                Preferences.sortPosition = 1
        //            }
        //
        //            includeLayoutSort.btnConfirm.setOnClickListener {
        //                categoryDetailViewModel.setDetailSortLayoutVisibility(false)
        //                textSort.text = Common.sortList[Preferences.sortPosition].sort
        //            }
        //
        //            imgFilter.setOnClickListener {
        //                categoryDetailViewModel.setFilterLayoutVisibility(true)
        //            }
        //
        //            includeLayoutFilter.imgClose.setOnClickListener {
        //                categoryDetailViewModel.setFilterLayoutVisibility(false)
        //            }
        //
        //            includeLayoutSort.sortRecyclerview.apply {
        //                layoutManager = LinearLayoutManager(view.context)
        //                categorySortAdapter = CategorySortAdapter { position ->
        //                    onItemClicked(position)
        //                    Preferences.sortPosition = position
        //                }
        //
        //                categorySortAdapter.submitList(Common.sortList)
        //                adapter = categorySortAdapter
        //                categorySortAdapter.setSelectedPosition(1)
        //            }
        //
        //            includeLayoutFilter.recyclerviewArea.apply {
        //                layoutManager = LinearLayoutManager(view.context)
        //                categoryFilterAreaAdapter = CategoryFilterAreaAdapter()
        //                adapter = categoryFilterAreaAdapter
        //                categoryFilterAreaAdapter.submitList(Common.categoryDetailAreaList)
        //
        //                categoryFilterAreaAdapter.setOnItemClickListener {
        //                    if (categoryFilterAreaAdapter.selectedList().size == 0) {
        //                        includeLayoutFilter.textAreaAll.setTextColor(
        //                            ContextCompat.getColor(
        //                                this.context,
        //                                R.color.black
        //                            )
        //                        )
        //                        includeLayoutFilter.checkboxAreaAll.show()
        //                    } else {
        //                        includeLayoutFilter.textAreaAll.setTextColor(
        //                            ContextCompat.getColor(
        //                                this.context,
        //                                R.color.gray_B7B7B7
        //                            )
        //                        )
        //                        includeLayoutFilter.checkboxAreaAll.hide()
        //                    }
        //
        //                    categoryDetailViewModel.setFilterAreaList(categoryFilterAreaAdapter.selectedList())
        //                }
        //            }
        //
        //        }
        //
        //
        //
        //        binding.recyclerviewTicket.apply {
        //            layoutManager = LinearLayoutManager(view.context)
        //            pagingAdapter = PagingAdapter {
        //                itemClicked(it.id)
        //            }
        //
        //            adapter = pagingAdapter
        //        }
        //
        //        lifecycleScope.launch {
        //            categoryDetailViewModel.setGenre(args.item).collectLatest {
        //                try {
        //                    Log.e("!!!!!!!!!!", it.toString())
        //                    pagingAdapter.submitData(it)
        //                } catch (e: Exception) {
        //                    Log.e("!!!", e.toString())
        //                }
        //            }
        //        }
        //
        //        backPressed()*/
    }

    private fun checkBoxWatcher(isCheck: Boolean, textView: TextView) {
        binding.apply {
            if (isCheck) {
                textView.setTextColor(
                    ContextCompat.getColor(
                        this.root.context,
                        R.color.black
                    )
                )
            } else {
                textView.setTextColor(
                    ContextCompat.getColor(
                        this.root.context,
                        R.color.gray_C8C8C8
                    )
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun filterViewWatcher(filterBtnType: CategoryDetailViewModel.FilterBtnType) {
        binding.apply {
            when (filterBtnType.name) {
                CategoryDetailViewModel.FilterBtnType.OPEN.name -> {
                    includeLayoutFilter.root.show()
                    Log.e("open",categoryDetailViewModel.statusList.value.toString())
                }

                CategoryDetailViewModel.FilterBtnType.CLOSE.name -> {
                    includeLayoutFilter.root.hide()
                }

                CategoryDetailViewModel.FilterBtnType.CLEAR.name -> {

                    // 진행상태 clear
                    categoryDetailViewModel.isChecked(CategoryDetailViewModel.Status.EMPTY)

                    // 예매 가격
                    categoryDetailViewModel.setPriceType(CategoryDetailViewModel.PriceType.EMPTY)
                }

                CategoryDetailViewModel.FilterBtnType.DONE.name -> {
                    includeLayoutFilter.root.hide()
                    // chip 지역
                    if (categoryFilterAreaAdapter.selectedList().size == 0) {
                        setChipFalse(binding.root.context, chipArea, "지역")
                    } else {
                        setChipTrue(binding.root.context, chipArea)

                        when (categoryFilterAreaAdapter.selectedList().size) {
                            0 -> {
                                chipArea.text = "전체"
                            }

                            1 -> {
                                chipArea.text =
                                    categoryDetailViewModel.filterAreaData.value!![0].name
                            }

                            else -> {
                                chipArea.text =
                                    "지역 ${categoryDetailViewModel.filterAreaData.value!!.size}"
                            }
                        }
                    }

                    // 공연일
                    if (categoryDetailViewModel.selectedDay.value.isNullOrEmpty()) {
                        setChipFalse(binding.root.context, chipDay, "공연일")
                    } else {
                        setChipTrue(binding.root.context, chipDay)
                        chipDay.text =
                            categoryDetailViewModel.selectedDay.value.toString()
                    }

                    // 진행상태
                    val statusResources: List<String>
                    if (categoryDetailViewModel.statusList.value.isNullOrEmpty()) {
                        setChipFalse(binding.root.context, chipStatus, "진행 상태")
                    } else {
                        setChipTrue(binding.root.context, chipStatus)

                        statusResources = categoryDetailViewModel.statusList.value!!.map {
                            when (it) {
                                CategoryDetailViewModel.Status.COMPLETED -> {
                                    getString(CategoryDetailViewModel.Status.COMPLETED.res)
                                }

                                CategoryDetailViewModel.Status.ONGOING -> {
                                    getString(CategoryDetailViewModel.Status.ONGOING.res)
                                }

                                CategoryDetailViewModel.Status.UPCOMING -> {
                                    getString(CategoryDetailViewModel.Status.UPCOMING.res)
                                }

                                CategoryDetailViewModel.Status.EMPTY -> {
                                    getString(CategoryDetailViewModel.Status.EMPTY.res)
                                }
                            }
                        }
                        chipStatus.text = StringUtil.join(statusResources, ", ").toString()
                    }

                    // 예매 가격
                    setChipTrue(root.context, chipPrice)
                    chipPrice.text = getString(categoryDetailViewModel.priceType.value!!.res)
                }
            }
        }

    }

    override fun backPressed() {
        findNavController().popBackStack()
    }

    private fun onItemClicked(position: Int) {
        categorySortAdapter.setSelectedPosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Preferences.sortPosition = 1
    }

    //    private fun setTextViewSize(textView: TextView, data: String) {
//        // 높이와 너비를 WRAP_CONTENT로 설정합니다.
//        textView.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//
//        textView.text = data
//
//        // 새로운 크기를 설정합니다.
//        textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//        val width = textView.measuredWidth
//        val height = textView.measuredHeight
//
//        textView.layoutParams = LinearLayout.LayoutParams(width, height)
//    }
//
//    private fun setChipTrue(context: Context, chip: Chip) {
//        chip.chipBackgroundColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
//        chip.chipStrokeColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
//        chip.isCloseIconVisible = true
//        chip.setTextColor(ContextCompat.getColor(context, R.color.white))
//    }
//
//    private fun setChipFalse(context: Context, chip: Chip, text: String) {
//        chip.chipBackgroundColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
//        chip.chipStrokeColor =
//            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray_EFEFEF))
//        chip.isCloseIconVisible = false
//        chip.setTextColor(ContextCompat.getColor(context, R.color.gray_949494))
//        chip.text = text
//    }
//
//
    private fun setChipTrue(context: Context, chip: Chip) {
        chip.apply {
            chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
            chipStrokeColor =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
            setTextColor(ContextCompat.getColor(context, R.color.white))
            isCloseIconVisible = true
        }
    }

    //
//    // 필터 진행상태 초기화
//    private fun initFilterStatus() {
//        binding.includeLayoutFilter.checkboxDuring.isSelected = false
//        binding.includeLayoutFilter.checkboxPlanned.isSelected = false
//        binding.includeLayoutFilter.checkboxFinish.isSelected = false
//        binding.includeLayoutFilter.textPerformanceDuring.isSelected = false
//        binding.includeLayoutFilter.textPerformancePlanned.isSelected = false
//        binding.includeLayoutFilter.textPerformanceFinish.isSelected = false
//        categoryDetailViewModel.statusList.value?.clear()
//    }
//
//    private fun initFilterPrice() {
//        binding.includeLayoutFilter.radioFilterPriceAll.isChecked = true
//        binding.includeLayoutFilter.radioFilterPrice1.isChecked = false
//        binding.includeLayoutFilter.radioFilterPrice4.isChecked = false
//        binding.includeLayoutFilter.radioFilterPrice7.isChecked = false
//        binding.includeLayoutFilter.radioFilterPrice10.isChecked = false
//        binding.includeLayoutFilter.radioFilterPriceOver.isChecked = false
//        categoryDetailViewModel.clearFilterPrice()
//    }
//
    private fun initFilterDay() {
//        categoryDetailViewModel.clearSelectedDay()
        categoryDetailViewModel.setSelectedDay(null)
        binding.includeLayoutFilter.performanceCalendarView.clearSelection()
    }

    //
//    // 필터 지역 초기화
    @SuppressLint("NotifyDataSetChanged")
    private fun initFilterArea(context: Context) {
        binding.apply {
            includeLayoutFilter.apply {
                recyclerviewArea.removeAllViewsInLayout()
                textAreaAll.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.black
                    )
                )
                checkboxAreaAll.show()
            }
            categoryFilterAreaAdapter.apply {
                clear()
                submitList(Common.areaList)
                notifyDataSetChanged()
            }

            categoryDetailViewModel.filterAreaData.value?.clear()
        }
    }

    //
//    private fun itemClicked(id: String) {
//        findNavController().navigate(
//            CategoryDetailFragmentDirections.actionCategoryDetailFragmentToTicketDetailFragment(
//                id
//            )
//        )
//    }
//
//    private fun setUpObserver() {
//        categoryDetailViewModel.filterAreaData.observe(viewLifecycleOwner, ::isFilterAreaWatcher)
//        categoryDetailViewModel.statusList.observe(viewLifecycleOwner, ::isFilterChipWatcher)
//    }
//
//    private fun isFilterAreaWatcher(list: ArrayList<CategoryDetailArea>) {
//        if (list.isEmpty()) {
//            setChipFalse1(binding.chipArea, "지역")
//        } else {
//            setChipTrue1(binding.chipArea)
//
//            when (list.size) {
//                0 -> {
//                    binding.chipArea.text = "전체"
//                }
//
//                1 -> {
//                    binding.chipArea.text =
//                        categoryDetailViewModel.filterAreaData.value!![0].area
//                }
//
//                else -> {
//                    binding.chipArea.text =
//                        "지역 ${categoryDetailViewModel.filterAreaData.value?.size}"
//                }
//            }
//        }
//    }
//
//    private fun isFilterChipWatcher(list: ArrayList<String>) {
//        if (list.isEmpty()) {
//            setChipFalse1(binding.chipStatus, "진행 상태")
//        } else {
//            setChipTrue1(binding.chipStatus)
//            binding.chipStatus.text =
//                StringUtil.join(list, ", ").toString()
//        }
//    }
    private fun setChipFalse(context: Context, chip: Chip, text: String) {
        chip.apply {
            chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
            chipStrokeColor =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray_EFEFEF))
            isCloseIconVisible = false
            setTextColor(ContextCompat.getColor(context, R.color.gray_949494))
            this.text = text
        }
    }
}


