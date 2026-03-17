package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.CategoryDetailArea
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemFilterAreaBinding

//todo_sypark RecommendAreaAdapter처럼 변경 예정
class CategoryFilterAreaAdapter :
    ListAdapter<CategoryDetailArea, CategoryFilterAreaAdapter.ViewHolder>(DiffUtils()) {

    private var selectedArea = arrayListOf<CategoryDetailArea>()

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
            itemText.text = getItem(position).area

            itemText.setOnClickListener {
                applySelection(this, getItem(position))
                onItemClickListener?.let { it(getItem(position)) }
            }
        }
    }

    private class DiffUtils : DiffUtil.ItemCallback<CategoryDetailArea>() {
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

    private var onItemClickListener: ((CategoryDetailArea) -> Unit)? = null

    fun setOnItemClickListener(listener: (CategoryDetailArea) -> Unit) {
        onItemClickListener = listener
    }

    fun selectedList(): ArrayList<CategoryDetailArea> {
        return selectedArea
    }

    fun clear() {
        selectedArea.clear()
    }


    private fun applySelection(
        binding: ItemFilterAreaBinding,
        categoryDetailArea: CategoryDetailArea
    ) {
        if (selectedArea.contains(categoryDetailArea)) {
            selectedArea.remove(categoryDetailArea)
            changeTextColor(binding, R.color.gray_B7B7B7)
            binding.itemCheckbox.visibility = View.GONE
        } else {
            selectedArea.add(categoryDetailArea)
            changeTextColor(binding, R.color.black)
            binding.itemCheckbox.visibility = View.VISIBLE
        }
    }

    private fun changeTextColor(binding: ItemFilterAreaBinding, resId: Int) {
        binding.itemText.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                resId
            )
        )
    }
}




