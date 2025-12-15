package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.CategoryDetailSort
import com.sypark.openTicket.R

class CategorySortAdapter(
    private val sortList: List<CategoryDetailSort>,
    private val clickListener: (CategoryDetailSort) -> Unit
) :
    RecyclerView.Adapter<CategorySortHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySortHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_sort, parent, false)
        return CategorySortHolder(listItem)
    }

    override fun onBindViewHolder(holder: CategorySortHolder, position: Int) {
        val item = sortList[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int {
        return sortList.size
    }
}

class CategorySortHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(sortList: CategoryDetailSort, clickListener: (CategoryDetailSort) -> Unit) {
        val layoutCategorySort = view.findViewById<RelativeLayout>(R.id.layout_item_sort)
        val categoryText = view.findViewById<TextView>(R.id.text_sort)
        categoryText.text = sortList.sort

        layoutCategorySort.setOnClickListener {
            clickListener(sortList)
        }
    }
}