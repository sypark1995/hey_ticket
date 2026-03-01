package com.sypark.openTicket.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.Genre
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemGenreFilterBinding

class GenreAdapter(private val onItemClickListener: (Int, Genre) -> Unit) :
    ListAdapter<Genre, ViewHolder>(MyItemCallback()) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGenreFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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

class ViewHolder(val binding: ItemGenreFilterBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("CutPasteId")
    fun bind(
        item: Genre,
        isSelected: Boolean,
        onItemClickListener: (Int, Genre) -> Unit
    ) {
        binding.textGenre.apply {
            setOnClickListener {
                onItemClickListener(bindingAdapterPosition, item)
            }

            this.isSelected = isSelected
            text = item.genrenm

            val boldFont = ResourcesCompat.getFont(binding.root.context, R.font.pretendard_bold)
            val lightFont = ResourcesCompat.getFont(binding.root.context, R.font.pretendard_light)

            if (this.isSelected) {
                typeface = boldFont
                setBackgroundResource(R.drawable.round_16_black)
                setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {
                typeface = lightFont
                setBackgroundResource(R.drawable.round_16_gray)
                setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_989CA1))
            }
        }
    }
}