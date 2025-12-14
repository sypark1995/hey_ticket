package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.R

class CategorySortAdapter() : RecyclerView.Adapter<CategorySortHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySortHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_sort, parent, false)
        return CategorySortHolder(listItem)
    }

    override fun onBindViewHolder(holder: CategorySortHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 5
    }
}

class CategorySortHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind() {
        val categoryText = view.findViewById<TextView>(R.id.text_sort)
        categoryText.text = "최신순"
    }
}