package com.sypark.openTicket.base.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewHolder<I>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(holder: BaseRecyclerViewHolder<I>, position: Int, item: I)
}