package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.R

class CategoryFilterAreaAdapter() : ListAdapter<String, ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_filter_area, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemText.text = item
        holder.itemCheckbox.isChecked = true
        holder.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // handle checkbox state change
            Log.e("지역", holder.itemText.text.toString())
            Log.e("isChecked", isChecked.toString())
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemText: TextView = itemView.findViewById(R.id.item_text)
    val itemCheckbox: CheckBox = itemView.findViewById(R.id.item_checkbox)
}

