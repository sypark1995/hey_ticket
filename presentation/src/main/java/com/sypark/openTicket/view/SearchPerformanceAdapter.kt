package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.Genre
import com.sypark.openTicket.databinding.ItemCategoryBinding

class SearchPerformanceAdapter(private val clickListener: () -> Unit) :
    ListAdapter<Genre, CategoryHolder>(MyItemCallback()) {

    class MyItemCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(
            oldItem: Genre,
            newItem: Genre
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

    // todo_sypark 데이터 들어올 시 변경
    fun bind(item: Genre, clickListener: () -> Unit) {
        binding.apply {
            searchCategorySort.text = item.genrenm
            searchPerformanceCount.text = "1,341"
            this.root.setOnClickListener {
                clickListener()
            }
        }
    }
}