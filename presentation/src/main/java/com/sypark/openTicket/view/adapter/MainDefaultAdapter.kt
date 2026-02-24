package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sypark.data.db.entity.Ticket
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemDefaultTicketBinding

class MainDefaultAdapter(private val onItemClickListener: (Ticket) -> Unit) :
    ListAdapter<Ticket, DefaultViewHolder>(MyItemCallback()) {

    class MyItemCallback : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(
            oldItem: Ticket,
            newItem: Ticket
        ): Boolean {
            return oldItem.mt20id == newItem.mt20id
        }

        override fun areContentsTheSame(
            oldItem: Ticket,
            newItem: Ticket
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val binding = ItemDefaultTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class DefaultViewHolder(val binding: ItemDefaultTicketBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Ticket, onItemClickListener: (Ticket) -> Unit) {
        binding.apply {
            this.root.setOnClickListener {
                onItemClickListener(item)
            }

            textPlace.text = item.place
            textTitle.text = item.title
            textDate.text = item.startDate
            Glide.with(binding.root.context)
                .load(item.poster)
                .error(R.drawable.icon_default_poster)
                .into(imgPoster)
        }
    }
}