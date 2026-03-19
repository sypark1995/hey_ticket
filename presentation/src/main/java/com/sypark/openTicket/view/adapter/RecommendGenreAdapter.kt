package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.Genre
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRecommendGenreBinding

class RecommendGenreAdapter(private val onItemClickListener: (ArrayList<Genre>) -> Unit) :
    ListAdapter<Genre, RecommendGenreHolder>(MyItemCallback()) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendGenreHolder {
        val binding =
            ItemRecommendGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendGenreHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendGenreHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class RecommendGenreHolder(val binding: ItemRecommendGenreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Genre, onItemClickListener: (ArrayList<Genre>) -> Unit) {
        binding.apply {
            this.root.setOnClickListener {
                applySelection(binding, item)
                onItemClickListener(Common.selectedGenreList)
            }

            textGenre.text = item.genrenm
        }
    }

    private fun applySelection(
        binding: ItemRecommendGenreBinding,
        genre: Genre
    ) {
        if (Common.selectedGenreList.contains(genre)) {
            Common.selectedGenreList.remove(genre)
            changeTextColor(binding, R.color.gray_B7B7B7)
            binding.textGenre.setBackgroundResource(R.drawable.round_16_gray_white)
        } else {
            Common.selectedGenreList.add(genre)
            changeTextColor(binding, R.color.white)
            binding.textGenre.setBackgroundResource(R.drawable.round_16_black)
        }
    }

    private fun changeTextColor(binding: ItemRecommendGenreBinding, resId: Int) {
        binding.textGenre.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                resId
            )
        )
    }
}