package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sypark.data.db.entity.CategoryDetailArea
import com.sypark.data.db.entity.CategoryDetailSort
import com.sypark.openTicket.Preferences
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentCategoryDetailBinding
import com.sypark.openTicket.model.CategoryDetailViewModel
import com.sypark.openTicket.view.CategoryFilterAreaAdapter
import com.sypark.openTicket.view.CategorySortAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragment :
    BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()
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
            Log.e("price", categoryDetailViewModel.filterPrice.value.toString())

            binding.includeLayoutFilter.root.visibility = View.GONE

            // 높이와 너비를 WRAP_CONTENT로 설정합니다.
            binding.textArea.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            binding.textArea.text = "1111111111111"

            // 새로운 크기를 설정합니다.
            binding.textArea.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val width = binding.textArea.measuredWidth
            val height = binding.textArea.measuredHeight

            binding.textArea.layoutParams = LinearLayout.LayoutParams(width, height)
            setTextViewSize(binding.textArea, "1111111111")
        }
        binding.includeLayoutFilter.textAreaAll.setOnClickListener {

            binding.includeLayoutFilter.recyclerviewArea.removeAllViewsInLayout()
            categoryFilterAreaAdapter.apply {
                clear()
                submitList(categoryDetailAreaList)
                notifyDataSetChanged()
            }

            binding.includeLayoutFilter.textAreaAll.setTextColor(
                ContextCompat.getColor(
                    it.context,
                    R.color.black
                )
            )
            binding.includeLayoutFilter.checkboxAreaAll.visibility = View.VISIBLE

        }

//        binding.includeLayoutFilter.performanceCalendarView.setOnDateChangedListener { widget, date, selected ->
//            date.apply {
//            }
//        }

        binding.includeLayoutFilter.textPerformancePlanned.setOnClickListener {
            categoryDetailViewModel.isPlanedChecked()
        }
        binding.includeLayoutFilter.checkboxPlanned.setOnClickListener {
            categoryDetailViewModel.isPlanedChecked()
        }

        binding.includeLayoutFilter.textPerformanceDuring.setOnClickListener {
            categoryDetailViewModel.isDuringChecked()
        }
        binding.includeLayoutFilter.checkboxDuring.setOnClickListener {
            categoryDetailViewModel.isDuringChecked()
        }

        binding.includeLayoutFilter.textPerformanceFinish.setOnClickListener {
            categoryDetailViewModel.isFinishedChecked()
        }
        binding.includeLayoutFilter.checkboxFinish.setOnClickListener {
            categoryDetailViewModel.isFinishedChecked()
        }

        categoryDetailViewModel.isPlaned.observe(this) {
            binding.includeLayoutFilter.textPerformancePlanned.isSelected = it
            binding.includeLayoutFilter.checkboxPlanned.isSelected = it
        }

        categoryDetailViewModel.isFinished.observe(this) {
            binding.includeLayoutFilter.textPerformanceFinish.isSelected = it
            binding.includeLayoutFilter.checkboxFinish.isSelected = it
        }

        categoryDetailViewModel.isDuring.observe(this) {
            binding.includeLayoutFilter.textPerformanceDuring.isSelected = it
            binding.includeLayoutFilter.checkboxDuring.isSelected = it
        }

        binding.includeLayoutFilter.radioFilterArea.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.VISIBLE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
        }

        binding.includeLayoutFilter.radioFilterDay.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
        }

        binding.includeLayoutFilter.radioFilterStatus.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.VISIBLE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.GONE
        }

        binding.includeLayoutFilter.radioFilterPrice.setOnClickListener {
            binding.includeLayoutFilter.layoutFilterArea.visibility = View.GONE
            binding.includeLayoutFilter.layoutFilterPrice.visibility = View.VISIBLE
            binding.includeLayoutFilter.layoutPerformanceState.visibility = View.GONE
        }

        binding.includeLayoutFilter.radioGroupFilterPrice.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_filter_price_all -> {
                    categoryDetailViewModel.setFilterPrice("all")
                }
                R.id.radio_filter_price_1 -> {
                    categoryDetailViewModel.setFilterPrice("1")
                }
                R.id.radio_filter_price_4 -> {
                    categoryDetailViewModel.setFilterPrice("4")
                }
                R.id.radio_filter_price_7 -> {
                    categoryDetailViewModel.setFilterPrice("7")
                }
                R.id.radio_filter_price_10 -> {
                    categoryDetailViewModel.setFilterPrice("10")
                }
                R.id.radio_filter_price_over -> {
                    categoryDetailViewModel.setFilterPrice("over")
                }
            }
        }
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
}
