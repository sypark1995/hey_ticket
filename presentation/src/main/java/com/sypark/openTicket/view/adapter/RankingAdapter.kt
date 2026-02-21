package com.sypark.openTicket.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.Genre
import com.sypark.openTicket.R

class RankingAdapter(private val onItemClickListener: (Int, Genre) -> Unit) :
    ListAdapter<Genre, ViewHolder>(MyItemCallback()) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position == selectedPosition, onItemClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    class MyItemCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean {
            return oldItem == newItem
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("CutPasteId")
    fun bind(
        item: Genre,
        isSelected: Boolean,
        onItemClickListener: (Int, Genre) -> Unit
    ) {
        itemView.findViewById<RadioButton>(R.id.text_genre)
            .setOnClickListener { onItemClickListener(bindingAdapterPosition, item) }
        itemView.isSelected = isSelected

        itemView.findViewById<RadioButton>(R.id.text_genre).text = item.genrenm

        if (itemView.isSelected) {
            itemView.findViewById<RadioButton>(R.id.text_genre)
                .setBackgroundResource(R.drawable.round_16_black)
            itemView.findViewById<RadioButton>(R.id.text_genre)
                .setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
        } else {
            itemView.findViewById<TextView>(R.id.text_genre)
                .setBackgroundResource(R.drawable.round_16_gray)
            itemView.findViewById<TextView>(R.id.text_genre)
                .setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_989CA1))
        }

    }
}