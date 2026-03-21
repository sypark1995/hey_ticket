package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.databinding.ItemRecommendKeywordBinding

class RecommendKeywordAdapter(private val onItemClickListener: (String) -> Unit) :
    ListAdapter<String, RecommendKeywordHolder>(MyItemCallback()) {

    class MyItemCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendKeywordHolder {
        val binding =
            ItemRecommendKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendKeywordHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendKeywordHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class RecommendKeywordHolder(val binding: ItemRecommendKeywordBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String, onItemClickListener: (String) -> Unit) {
        binding.apply {
            textKeyword.text = item

            imgClose.setOnClickListener {
                onItemClickListener(item)
            }
        }
    }
}