package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.domain.model.GenreCount
import com.sypark.openTicket.Common
import com.sypark.openTicket.databinding.ItemCategoryBinding

class CategoryGenreAdapter(private val clickListener: (String) -> Unit) :
    ListAdapter<GenreCount, CategoryHolder>(MyItemCallback()) {

    class MyItemCallback : DiffUtil.ItemCallback<GenreCount>() {
        override fun areItemsTheSame(
            oldItem: GenreCount,
            newItem: GenreCount
        ): Boolean {
            return oldItem.genre == newItem.genre
        }

        override fun areContentsTheSame(
            oldItem: GenreCount,
            newItem: GenreCount
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class CategoryHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GenreCount, clickListener: (String) -> Unit) {
        binding.apply {

            //todo_sypark 로직 변경 예정 루프 계속 돌고 있음...
            Common.genreList.forEach {
                if (it.code == item.genre) {
                    searchCategorySort.text = it.genrenm
                }
            }
            // CategoryRepositoryImpl은 KOPIS API의 페이지당 최대 조회수(100건) 제한으로 건수를 계산하므로,
            // 100은 실제로 "100건 이상"을 의미한다.
            searchPerformanceCount.text = if (item.count >= 100) "100+" else item.count.toString()

            this.root.setOnClickListener {
                clickListener(item.genre)
            }
        }
    }
}