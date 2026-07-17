package com.sypark.openTicket.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.Genre
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRecommendAreaBinding

class RecommendGenreAdapter : ListAdapter<Genre, RecommendGenreAdapter.ViewHolder>(DiffCallback()) {

    private val selectedCodes = mutableSetOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedCodes(codes: Set<String>) {
        selectedCodes.clear()
        selectedCodes.addAll(codes)
        notifyDataSetChanged()
    }

    fun selectedCodes(): Set<String> = selectedCodes.toSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedCodes.contains(item.code)) {
            if (selectedCodes.contains(item.code)) {
                selectedCodes.remove(item.code)
            } else {
                selectedCodes.add(item.code)
            }
            notifyItemChanged(position)
        }
    }

    class ViewHolder(private val binding: ItemRecommendAreaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre, isSelected: Boolean, onClick: () -> Unit) {
            binding.textArea.apply {
                text = item.genrenm
                setOnClickListener { onClick() }
                if (isSelected) {
                    setBackgroundResource(R.drawable.round_16_black)
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    setBackgroundResource(R.drawable.round_16_gray_white)
                    setTextColor(ContextCompat.getColor(context, R.color.gray_949494))
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem.code == newItem.code
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem == newItem
    }
}
