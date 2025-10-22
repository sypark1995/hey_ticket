package com.sypark.openTicket.view

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.R

class SortTicketAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val sortTicketList = arrayListOf<String>()
    private var tickClickListener: TicketClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kind_ticktet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return sortTicketList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layout: ConstraintLayout = view.findViewById(R.id.layout_ticket_sort)
        var name: TextView = view.findViewById(R.id.text_ticket_name)
        var img: ImageView = view.findViewById(R.id.img_ticket_sort)

        fun bind(position: Int) {
            val data = sortTicketList[position]

            name.text = data

            //todo_designer 콘서트,뮤지컬,연극 이미지 아이콘
            when (name.text) {
                itemView.context.getString(R.string.concert) -> {
                    img.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_launcher_background
                    )
                }

                itemView.context.getString(R.string.musical) -> {
                    img.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_launcher_background
                    )
                }

                itemView.context.getString(R.string.drama) -> {
                    img.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_launcher_background
                    )
                }
            }

            layout.setOnClickListener {
                tickClickListener?.onClick(name.text.toString())
            }
        }
    }

    fun setListInfo(tickList: ArrayList<String>) {
        sortTicketList.clear()
        sortTicketList.addAll(tickList)
    }
    fun setTicketClickListener(listener: TicketClickListener) {
        tickClickListener = listener
    }
}

interface TicketClickListener {
    fun onClick(string: String)
}