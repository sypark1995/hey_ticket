package com.sypark.openTicket.view.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.sypark.data.db.entity.CategoryDetailArea
import com.sypark.data.db.entity.CategoryDetailSort
import com.sypark.data.db.entity.Ticket
import com.sypark.openTicket.Preferences
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryDetailBinding
import com.sypark.openTicket.model.CategoryDetailViewModel
import com.sypark.openTicket.view.CategoryFilterAreaAdapter
import com.sypark.openTicket.view.CategorySortAdapter
import com.sypark.openTicket.view.PagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jsoup.internal.StringUtil
import org.threeten.bp.format.TextStyle
import java.util.*

@AndroidEntryPoint
class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()
    private lateinit var pagingAdapter: PagingAdapter
    private lateinit var categorySortAdapter: CategorySortAdapter
    private lateinit var categoryFilterAreaAdapter: CategoryFilterAreaAdapter

    private val sortList = listOf(
        CategoryDetailSort("최근 등록순"),
        CategoryDetailSort("예매순"),
        CategoryDetailSort("조회수순")
    )

    private val categoryDetailAreaList = listOf(
        CategoryDetailArea("서울시"),
        CategoryDetailArea("경기도"),
        CategoryDetailArea("강원도"),
        CategoryDetailArea("충청북도"),
        CategoryDetailArea("충청남도"),
        CategoryDetailArea("전라북도"),
        CategoryDetailArea("전라남도"),
        CategoryDetailArea("경상남도"),
        CategoryDetailArea("경상북도"),
    )

    override fun init(view: View) {
        binding.imgBack.setOnClickListener {
            findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToCategoryFragment())
            Preferences.sortPosition = 1
        }

        binding.sortLayout.setOnClickListener {
            binding.includeLayoutSort.layoutDetailSort.visibility =
                View.VISIBLE   //todo_sypark viewmodel로 한번에 관리
        }

        binding.includeLayoutSort.imgClose.setOnClickListener {
            binding.includeLayoutSort.layoutDetailSort.visibility =
                View.GONE  //todo_sypark viewmodel로 한번에 관리

            Preferences.sortPosition = 1
        }

        binding.includeLayoutSort.sortRecyclerview.apply {
            layoutManager = LinearLayoutManager(view.context)
            categorySortAdapter = CategorySortAdapter { position ->
                onItemClicked(position)
                Preferences.sortPosition = position
            }

            categorySortAdapter.submitList(sortList)
            adapter = categorySortAdapter
            categorySortAdapter.setSelectedPosition(1)
        }

        binding.includeLayoutSort.btnConfirm.setOnClickListener {
            binding.includeLayoutSort.layoutDetailSort.visibility =
                View.GONE  //todo_sypark viewmodel로 한번에 관리

            binding.textSort.text = sortList[Preferences.sortPosition].sort
        }

        binding.imgFilter.setOnClickListener {
            binding.includeLayoutFilter.root.visibility = View.VISIBLE
        }

        binding.includeLayoutFilter.imgClose.setOnClickListener {
            binding.includeLayoutFilter.root.visibility = View.GONE
        }

        binding.includeLayoutFilter.recyclerviewArea.apply {
            layoutManager = LinearLayoutManager(view.context)
            categoryFilterAreaAdapter = CategoryFilterAreaAdapter()
            adapter = categoryFilterAreaAdapter
            categoryFilterAreaAdapter.submitList(categoryDetailAreaList)

            categoryFilterAreaAdapter.setOnItemClickListener {
                if (categoryFilterAreaAdapter.selectedList().size == 0) {
                    binding.includeLayoutFilter.textAreaAll.setTextColor(
                        ContextCompat.getColor(
                            this.context,
                            R.color.black
                        )
                    )
                    binding.includeLayoutFilter.checkboxAreaAll.visibility = View.VISIBLE
                } else {
                    binding.includeLayoutFilter.textAreaAll.setTextColor(
                        ContextCompat.getColor(
                            this.context,
                            R.color.gray_B7B7B7
                        )
                    )
                    binding.includeLayoutFilter.checkboxAreaAll.visibility = View.GONE
                }

                categoryDetailViewModel.setFilterAreaList(categoryFilterAreaAdapter.selectedList())
            }
        }

        categoryDetailViewModel.filterAreaData.observe(this) {

        }


        binding.includeLayoutFilter.btnConfirm.setOnClickListener {

            Log.e("!!!", categoryDetailViewModel.filterAreaData.value.toString())
            Log.e("isPlaned", categoryDetailViewModel.isPlaned.value.toString())
            Log.e("isDuring", categoryDetailViewModel.isDuring.value.toString())
            Log.e("isFinished", categoryDetailViewModel.isFinished.value.toString())
            Log.e("price", categoryDetailViewModel.filterPriceData.value.toString())

            if (categoryDetailViewModel.filterAreaData.value != null && categoryDetailViewModel.filterAreaData.value!!.size > 0) {
                setChipTrue(it.context, binding.chipArea)
                when (categoryDetailViewModel.filterAreaData.value?.size) {
                    null, 0 -> {
                        binding.chipArea.text = "전체"
                    }
                    1 -> {
                        binding.chipArea.text =
                            categoryDetailViewModel.filterAreaData.value!![0].area
                    }
                    else -> {
                        binding.chipArea.text =
                            "지역 ${categoryDetailViewModel.filterAreaData.value?.size}"
                    }
                }
            } else {
                setChipFalse(it.context, binding.chipArea, "지역")
            }

            if (categoryDetailViewModel.statusList.value?.size == 0) {
                setChipFalse(it.context, binding.chipStatus, "진행 상태")
            } else {
                setChipTrue(it.context, binding.chipStatus)
                binding.chipStatus.text =
                    StringUtil.join(categoryDetailViewModel.statusList.value, ", ").toString()
            }

            if (categoryDetailViewModel.filterPriceData.value.isNullOrEmpty()) {
                setChipFalse(it.context, binding.chipPrice, "예매가격")
            } else {
                setChipTrue(it.context, binding.chipPrice)
                binding.chipPrice.text = categoryDetailViewModel.filterPriceData.value.toString()
            }

            if (categoryDetailViewModel.selectedDay.value.isNullOrEmpty()) {
                setChipFalse(it.context, binding.chipDay, "공연일")
            } else {
                setChipTrue(it.context, binding.chipDay)
                binding.chipDay.text = categoryDetailViewModel.selectedDay.value.toString()
            }

            binding.includeLayoutFilter.root.visibility = View.GONE
        }

        binding.chipArea.apply {

            setOnClickListener {
                binding.includeLayoutFilter.radioFilterArea.isChecked = true
                binding.includeLayoutFilter.root.visibility = View.VISIBLE

                binding.includeLayoutFilter.layoutFilterArea.visibility = View.VISIBLE
                binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
                binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
                binding.includeLayoutFilter.layoutFilterDay.visibility = View.GONE
            }

            setOnCloseIconClickListener {
                setChipFalse(it.context, this, "지역")
                initFilterArea(it.context)
            }
        }
        binding.chipDay.apply {
            setOnClickListener {
                binding.includeLayoutFilter.radioFilterDay.isChecked = true
                binding.includeLayoutFilter.root.visibility = View.VISIBLE

                binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
                binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
                binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
                binding.includeLayoutFilter.layoutFilterDay.visibility = View.VISIBLE
            }
            setOnCloseIconClickListener {
                setChipFalse(it.context, this, "공연일")
                initFilterDay()
            }
        }

        binding.chipStatus.apply {

            setOnClickListener {
                binding.includeLayoutFilter.radioFilterStatus.isChecked = true
                binding.includeLayoutFilter.root.visibility = View.VISIBLE

                binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
                binding.includeLayoutFilter.layoutPerformanceState.visibility = View.VISIBLE
                binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
                binding.includeLayoutFilter.layoutFilterDay.visibility = View.GONE
            }

            setOnCloseIconClickListener {
                setChipFalse(it.context, this, "진행상태")
                initFilterStatus()
            }
        }

        binding.includeLayoutFilter.layoutFilterReset.setOnClickListener {
            initFilterArea(it.context)
            setChipFalse(it.context, binding.chipArea, "지역")

            initFilterStatus()
            setChipFalse(it.context, binding.chipStatus, "진행상태")

            initFilterPrice()
            setChipFalse(it.context, binding.chipPrice, "예매가격")

            initFilterDay()
            setChipFalse(it.context, binding.chipDay, "공연일")
        }

        binding.includeLayoutFilter.textAreaAll.setOnClickListener {
            initFilterArea(it.context)
        }

        binding.chipPrice.apply {
            setOnClickListener {
                binding.includeLayoutFilter.radioFilterPrice.isChecked = true
                binding.includeLayoutFilter.root.visibility = View.VISIBLE

                binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
                binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
                binding.includeLayoutFilter.layoutFilterPrice.visibility = View.VISIBLE
                binding.includeLayoutFilter.layoutFilterDay.visibility = View.GONE
            }

            setOnCloseIconClickListener {
                setChipFalse(it.context, this, "예매 가격")
                initFilterPrice()
            }
        }
        binding.includeLayoutFilter.performanceCalendarView.apply {

        }

        binding.includeLayoutFilter.performanceCalendarView.setOnDateChangedListener { widget, date, selected ->
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
        binding.includeLayoutFilter.layoutPlanned.setOnClickListener {
            categoryDetailViewModel.isPlanedChecked()
        }

        binding.includeLayoutFilter.textPerformancePlanned.setOnClickListener {
            categoryDetailViewModel.isPlanedChecked()
        }
        binding.includeLayoutFilter.checkboxPlanned.setOnClickListener {
            categoryDetailViewModel.isPlanedChecked()
        }

        binding.includeLayoutFilter.layoutDuring.setOnClickListener {
            categoryDetailViewModel.isDuringChecked()
        }
        binding.includeLayoutFilter.textPerformanceDuring.setOnClickListener {
            categoryDetailViewModel.isDuringChecked()
        }
        binding.includeLayoutFilter.checkboxDuring.setOnClickListener {
            categoryDetailViewModel.isDuringChecked()
        }

        binding.includeLayoutFilter.layoutFinish.setOnClickListener {
            categoryDetailViewModel.isFinishedChecked()
        }

        binding.includeLayoutFilter.textPerformanceFinish.setOnClickListener {
            categoryDetailViewModel.isFinishedChecked()
        }
        binding.includeLayoutFilter.checkboxFinish.setOnClickListener {
            categoryDetailViewModel.isFinishedChecked()
        }

        val statusList = ArrayList<String>()

        categoryDetailViewModel.isPlaned.observe(this) {
            binding.includeLayoutFilter.textPerformancePlanned.isSelected = it
            binding.includeLayoutFilter.checkboxPlanned.isSelected = it
            if (it) {
                statusList.add(binding.includeLayoutFilter.textPerformancePlanned.text.toString())
                categoryDetailViewModel.setFilterStatus(statusList)
            } else {
                statusList.remove(binding.includeLayoutFilter.textPerformancePlanned.text.toString())
                categoryDetailViewModel.setFilterStatus(statusList)
            }
        }

        categoryDetailViewModel.isFinished.observe(this) {
            binding.includeLayoutFilter.textPerformanceFinish.isSelected = it
            binding.includeLayoutFilter.checkboxFinish.isSelected = it

            if (it) {
                statusList.add(binding.includeLayoutFilter.textPerformanceFinish.text.toString())
                categoryDetailViewModel.setFilterStatus(statusList)
            } else {
                statusList.remove(binding.includeLayoutFilter.textPerformanceFinish.text.toString())
                categoryDetailViewModel.setFilterStatus(statusList)
            }
        }

        categoryDetailViewModel.isDuring.observe(this) {
            binding.includeLayoutFilter.textPerformanceDuring.isSelected = it
            binding.includeLayoutFilter.checkboxDuring.isSelected = it

            if (it) {
                statusList.add(binding.includeLayoutFilter.textPerformanceDuring.text.toString())
                categoryDetailViewModel.setFilterStatus(statusList)
            } else {
                statusList.remove(binding.includeLayoutFilter.textPerformanceDuring.text.toString())
                categoryDetailViewModel.setFilterStatus(statusList)
            }
        }

        binding.includeLayoutFilter.radioFilterArea.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.VISIBLE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterDay.visibility = View.GONE
        }

        binding.includeLayoutFilter.radioFilterDay.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterDay.visibility = View.VISIBLE
        }

        binding.includeLayoutFilter.radioFilterStatus.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.VISIBLE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterDay.visibility = View.GONE
        }

        binding.includeLayoutFilter.radioFilterPrice.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.VISIBLE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterDay.visibility = View.GONE
        }

        binding.includeLayoutFilter.radioGroupFilterPrice.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_filter_price_1 -> {
                    categoryDetailViewModel.setFilterPrice("1만원 미만")
                }
                R.id.radio_filter_price_4 -> {
                    categoryDetailViewModel.setFilterPrice("1~4만원")
                }
                R.id.radio_filter_price_7 -> {
                    categoryDetailViewModel.setFilterPrice("4~7만원")
                }
                R.id.radio_filter_price_10 -> {
                    categoryDetailViewModel.setFilterPrice("7~10만원")
                }
                R.id.radio_filter_price_over -> {
                    categoryDetailViewModel.setFilterPrice("10만원 이상")
                }
            }
        }

//        binding.recyclerviewTicket.adapter = PagingAdapter()
        binding.recyclerviewTicket.apply {
            layoutManager = LinearLayoutManager(view.context)
            pagingAdapter = PagingAdapter {
                itemClicked(it)
            }

            adapter = pagingAdapter
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            categoryDetailViewModel.pagingData.collectLatest {
                try {
                    pagingAdapter.submitData(it)
                } catch (e: Exception) {
                    Log.e("!!!", e.toString())
                }
            }
        }

        backPressed()
    }

    private fun onItemClicked(position: Int) {
        categorySortAdapter.setSelectedPosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Preferences.sortPosition = 1
    }

    private fun setTextViewSize(textView: TextView, data: String) {
        // 높이와 너비를 WRAP_CONTENT로 설정합니다.
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        textView.text = data

        // 새로운 크기를 설정합니다.
        textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width = textView.measuredWidth
        val height = textView.measuredHeight

        textView.layoutParams = LinearLayout.LayoutParams(width, height)
    }

    private fun setChipTrue(context: Context, chip: Chip) {
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
        chip.chipStrokeColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black_111111))
        chip.isCloseIconVisible = true
        chip.setTextColor(ContextCompat.getColor(context, R.color.white))
    }

    private fun setChipFalse(context: Context, chip: Chip, text: String) {
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
        chip.chipStrokeColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray_EFEFEF))
        chip.isCloseIconVisible = false
        chip.setTextColor(ContextCompat.getColor(context, R.color.gray_949494))
        chip.text = text
    }

    // 필터 지역 초기화
    private fun initFilterArea(context: Context) {
        binding.includeLayoutFilter.recyclerviewArea.removeAllViewsInLayout()
        categoryFilterAreaAdapter.apply {
            clear()
            submitList(categoryDetailAreaList)
            notifyDataSetChanged()
        }

        binding.includeLayoutFilter.textAreaAll.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.black
            )
        )
        categoryDetailViewModel.filterAreaData.value?.clear()
        binding.includeLayoutFilter.checkboxAreaAll.visibility = View.VISIBLE
    }

    // 필터 진행상태 초기화
    private fun initFilterStatus() {
        binding.includeLayoutFilter.checkboxDuring.isSelected = false
        binding.includeLayoutFilter.checkboxPlanned.isSelected = false
        binding.includeLayoutFilter.checkboxFinish.isSelected = false
        binding.includeLayoutFilter.textPerformanceDuring.isSelected = false
        binding.includeLayoutFilter.textPerformancePlanned.isSelected = false
        binding.includeLayoutFilter.textPerformanceFinish.isSelected = false
        categoryDetailViewModel.statusList.value?.clear()
    }

    private fun initFilterPrice() {
        binding.includeLayoutFilter.radioFilterPriceAll.isChecked = true
        binding.includeLayoutFilter.radioFilterPrice1.isChecked = false
        binding.includeLayoutFilter.radioFilterPrice4.isChecked = false
        binding.includeLayoutFilter.radioFilterPrice7.isChecked = false
        binding.includeLayoutFilter.radioFilterPrice10.isChecked = false
        binding.includeLayoutFilter.radioFilterPriceOver.isChecked = false
        categoryDetailViewModel.clearFilterPrice()
    }

    private fun initFilterDay() {
        categoryDetailViewModel.clearSelectedDay()
        binding.includeLayoutFilter.performanceCalendarView.clearSelection()
    }

    private fun backPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.includeLayoutFilter.root.visibility == View.VISIBLE || binding.includeLayoutSort.root.visibility == View.VISIBLE) {
                    binding.includeLayoutFilter.root.visibility = View.GONE
                    binding.includeLayoutSort.root.visibility = View.GONE
                } else {
                    findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToCategoryFragment())
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun itemClicked(data: Ticket) {
        findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToTicketDetailFragment(data))
    }
}
