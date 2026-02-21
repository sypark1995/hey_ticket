package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.R

class SearchPerformanceAdapter(private val clickListener: () -> Unit) :
    RecyclerView.Adapter<PerformanceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformanceHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_performance_category, parent, false)
        return PerformanceHolder(listItem)
    }

    override fun onBindViewHolder(holder: PerformanceHolder, position: Int) {
        holder.bind(clickListener)
    }

    override fun getItemCount(): Int {
        return 5
    }
}

class PerformanceHolder(val view: View) : RecyclerView.ViewHolder(view) {

    // todo_sypark 데이터 들어올 시 변경
    fun bind(clickListener: () -> Unit) {
        val performanceCategoryText = view.findViewById<TextView>(R.id.search_category_sort)
        performanceCategoryText.text = "콘서트"

        val performanceCategoryCount = view.findViewById<TextView>(R.id.search_performance_count)
        performanceCategoryCount.text = "1,341"

        view.findViewById<RelativeLayout>(R.id.layout_search_category_sort).setOnClickListener {
            clickListener()
        }
    }
}