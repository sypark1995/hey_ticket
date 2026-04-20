package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.Areas
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRecommendAreaBinding

class RecommendAreaAdapter(private val onItemClickListener: (ArrayList<Areas>) -> Unit) :
    ListAdapter<Areas, RecommendAreaHolder>(MyItemCallback()) {

    class MyItemCallback : DiffUtil.ItemCallback<Areas>() {
        override fun areItemsTheSame(
            oldItem: Areas,
            newItem: Areas
        ): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(
            oldItem: Areas,
            newItem: Areas
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
        item: Areas,
        onItemClickListener: (ArrayList<Areas>) -> Unit
    ) {
        binding.apply {
            this.root.setOnClickListener {
                applySelection(binding, item)
                onItemClickListener(Common.selectedAreaList)
            }
            textArea.text = item.name
        }
    }

    private fun applySelection(
        binding: ItemRecommendAreaBinding,
        areas: Areas
    ) {
        if (Common.selectedAreaList.contains(areas)) {
            Common.selectedAreaList.remove(areas)
            changeTextColor(binding, R.color.gray_949494)
            binding.textArea.setBackgroundResource(R.drawable.round_16_gray_white)
        } else {
            Common.selectedAreaList.add(areas)
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