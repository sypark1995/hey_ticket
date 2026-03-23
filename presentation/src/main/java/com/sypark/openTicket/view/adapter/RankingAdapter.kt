package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sypark.data.db.entity.Content
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRankingBinding

class RankingAdapter(private val onItemClickListener: (Content) -> Unit) :
    ListAdapter<Content, RankingViewHolder>(MyItemCallback()) {

    class MyItemCallback : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(
            oldItem: Content,
            newItem: Content
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Content,
            newItem: Content
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class RankingViewHolder(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Content, onItemClickListener: (Content) -> Unit) {
        binding.apply {
            this.root.setOnClickListener {
                onItemClickListener(item)
            }

            textRanking.text = item.rank.toString()
            textPlace.text = item.place
            textTitle.text = item.title
            textDate.text = item.startDate
            Glide.with(binding.root.context)
                .load(item.poster)
                .transform(CenterCrop(), RoundedCorners(25))
                .error(R.drawable.icon_default_poster)
                .into(imgPoster)
        }
    }
}