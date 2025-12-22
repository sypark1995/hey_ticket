package com.sypark.openTicket.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.CategoryDetailSort
import com.sypark.openTicket.R


class CategorySortAdapter(private val onItemClickListener: (Int) -> Unit) :
    ListAdapter<CategoryDetailSort, CategorySortAdapter.ViewHolder>(MyItemCallback()) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sort, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position == selectedPosition, onItemClickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: CategoryDetailSort,
            isSelected: Boolean,
            onItemClickListener: (Int) -> Unit
        ) {
            itemView.setOnClickListener { onItemClickListener(adapterPosition) }
            // 선택 여부에 따라서 UI 업데이트
            itemView.isSelected = isSelected

            itemView.findViewById<TextView>(R.id.text_sort).text = item.sort

            if (itemView.isSelected) {
                itemView.findViewById<ImageView>(R.id.img_check).visibility = View.VISIBLE
            } else {
                itemView.findViewById<ImageView>(R.id.img_check).visibility = View.GONE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    class MyItemCallback : DiffUtil.ItemCallback<CategoryDetailSort>() {
        override fun areItemsTheSame(
            oldItem: CategoryDetailSort,
            newItem: CategoryDetailSort
        ): Boolean {
            return oldItem.sort == newItem.sort
        }

        override fun areContentsTheSame(
            oldItem: CategoryDetailSort,
            newItem: CategoryDetailSort
        ): Boolean {
            return oldItem == newItem
        }
    }
}

