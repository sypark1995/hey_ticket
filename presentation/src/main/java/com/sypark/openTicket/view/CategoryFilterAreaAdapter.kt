package com.sypark.openTicket.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.Areas
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemFilterAreaBinding

class CategoryFilterAreaAdapter :
    ListAdapter<Areas, CategoryFilterAreaAdapter.ViewHolder>(DiffUtils()) {

    private var selectedArea: Areas? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterAreaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemFilterAreaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val area = getItem(position)
            itemText.text = area.name
            applyVisual(this, area == selectedArea)

            itemText.setOnClickListener {
                applySelection(area)
                onItemClickListener?.let { it(area) }
            }
        }
    }

    private class DiffUtils : DiffUtil.ItemCallback<Areas>() {
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

    private var onItemClickListener: ((Areas) -> Unit)? = null

    fun setOnItemClickListener(listener: (Areas) -> Unit) {
        onItemClickListener = listener
    }

    fun selectedList(): ArrayList<Areas> {
        return selectedArea?.let { arrayListOf(it) } ?: arrayListOf()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        selectedArea = null
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun applySelection(area: Areas) {
        selectedArea = if (selectedArea == area) null else area
        notifyDataSetChanged()
    }

    private fun applyVisual(binding: ItemFilterAreaBinding, isSelected: Boolean) {
        binding.itemCheckbox.visibility = if (isSelected) View.VISIBLE else View.GONE
        binding.itemText.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                if (isSelected) R.color.black else R.color.gray_B7B7B7
            )
        )
    }
}
