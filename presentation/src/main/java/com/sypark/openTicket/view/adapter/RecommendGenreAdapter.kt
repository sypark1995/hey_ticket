package com.sypark.openTicket.view.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
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
    var onSelectionChanged: (() -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedCodes(codes: Set<String>) {
        selectedCodes.clear()
        selectedCodes.addAll(codes)
        notifyDataSetChanged()
        onSelectionChanged?.invoke()
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
            onSelectionChanged?.invoke()
        }
    }

    class ViewHolder(private val binding: ItemRecommendAreaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre, isSelected: Boolean, onClick: () -> Unit) {
            binding.textArea.text = item.genrenm
            binding.root.setOnClickListener { onClick() }
            binding.iconCheck.visibility = if (isSelected) View.VISIBLE else View.GONE
            if (isSelected) {
                binding.root.setBackgroundResource(R.drawable.round_10_black_outline)
                binding.textArea.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.textArea.setTypeface(binding.textArea.typeface, Typeface.BOLD)
            } else {
                binding.root.setBackgroundResource(R.drawable.round_10_gray_white)
                binding.textArea.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_949494))
                binding.textArea.setTypeface(Typeface.DEFAULT)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem.code == newItem.code
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem == newItem
    }
}
