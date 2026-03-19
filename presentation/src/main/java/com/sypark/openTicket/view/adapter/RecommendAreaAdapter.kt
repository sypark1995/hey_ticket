package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.CategoryDetailArea
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRecommendAreaBinding

class RecommendAreaAdapter(private val onItemClickListener: (ArrayList<CategoryDetailArea>) -> Unit) :
    ListAdapter<CategoryDetailArea, RecommendAreaHolder>(MyItemCallback()) {

    class MyItemCallback : DiffUtil.ItemCallback<CategoryDetailArea>() {
        override fun areItemsTheSame(
            oldItem: CategoryDetailArea,
            newItem: CategoryDetailArea
        ): Boolean {
            return oldItem.area == newItem.area
        }

        override fun areContentsTheSame(
            oldItem: CategoryDetailArea,
            newItem: CategoryDetailArea
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendAreaHolder {
        val binding =
            ItemRecommendAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendAreaHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendAreaHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class RecommendAreaHolder(val binding: ItemRecommendAreaBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CategoryDetailArea,
        onItemClickListener: (ArrayList<CategoryDetailArea>) -> Unit
    ) {
        binding.apply {
            this.root.setOnClickListener {
                applySelection(binding, item)
                onItemClickListener(Common.selectedAreaList)
            }
            textArea.text = item.area
        }
    }

    private fun applySelection(
        binding: ItemRecommendAreaBinding,
        categoryDetailArea: CategoryDetailArea
    ) {
        if (Common.selectedAreaList.contains(categoryDetailArea)) {
            Common.selectedAreaList.remove(categoryDetailArea)
            changeTextColor(binding, R.color.gray_B7B7B7)
            binding.textArea.setBackgroundResource(R.drawable.round_16_gray_white)
        } else {
            Common.selectedAreaList.add(categoryDetailArea)
            changeTextColor(binding, R.color.white)
            binding.textArea.setBackgroundResource(R.drawable.round_16_black)
        }
    }

    private fun changeTextColor(binding: ItemRecommendAreaBinding, resId: Int) {
        binding.textArea.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                resId
            )
        )
    }
}