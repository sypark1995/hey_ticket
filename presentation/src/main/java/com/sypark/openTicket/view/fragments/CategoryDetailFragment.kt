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

    // 진행상태
    private lateinit var statusResources: List<String>

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
                Log.e("statusList", it.toString())
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
                    checkBoxWatcher(
                        categoryDetailViewModel.isPlaned.value!!,
                        includeLayoutFilter.textPerformancePlanned
                    )

                    checkBoxWatcher(
                        categoryDetailViewModel.isDuring.value!!,
                        includeLayoutFilter.textPerformanceDuring
                    )

                    checkBoxWatcher(
                        categoryDetailViewModel.isFinished.value!!,
                        includeLayoutFilter.textPerformanceFinish
                    )

                    includeLayoutFilter.apply {
                        checkboxPlanned.isChecked = categoryDetailViewModel.isPlaned.value!!
                        checkboxDuring.isChecked = categoryDetailViewModel.isDuring.value!!
                        checkboxFinish.isChecked = categoryDetailViewModel.isFinished.value!!
                    }
                }

                CategoryDetailViewModel.FilterBtnType.CLOSE.name -> {
                    includeLayoutFilter.root.hide()
                }

                CategoryDetailViewModel.FilterBtnType.CLEAR.name -> {
                    /** clear 시 viewModel 의  status 값을 하면 X
                     *  clear를 하더라도 DONE을 해서 확인을 한것이 아니기때문에 view의 status 를 변경
                     *
                     * */
                    // 진행상태 clear
//                    categoryDetailViewModel.isChecked(CategoryDetailViewModel.Status.EMPTY)
                    clearFilterStatus()

                    // 예매 가격
//                    categoryDetailViewModel.setPriceType(CategoryDetailViewModel.PriceType.EMPTY)
                }

                CategoryDetailViewModel.FilterBtnType.DONE.name -> {
                    includeLayoutFilter.root.hide()
                    // chip 지역
                    if (categoryDetailViewModel.filterAreaData.value.isNullOrEmpty()) {

                        setChipFalse(binding.root.context, chipArea, "지역")
                    } else {
                        setChipTrue(binding.root.context, chipArea)

                        when (categoryDetailViewModel.filterAreaData.value!!.size) {
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
                    Preferences.price = chipPrice.text.toString()
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


    private fun clearFilterStatus() {
        /**     filter 진행상태 view clear
         *
         * */
        binding.includeLayoutFilter.apply {
            checkBoxWatcher(false, textPerformanceDuring)
            checkBoxWatcher(false, textPerformancePlanned)
            checkBoxWatcher(false, textPerformanceFinish)
            checkboxDuring.isChecked = false
            checkboxPlanned.isChecked = false
            checkboxFinish.isChecked = false
        }
    }

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


