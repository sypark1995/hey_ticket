package com.sypark.openTicket.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sypark.data.db.entity.Ticket
import com.sypark.openTicket.databinding.ItemTicketBinding

class PagingAdapter: PagingDataAdapter<Ticket,PagingViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Ticket>() {
            override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagingViewHolder(
            ItemTicketBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}

class PagingViewHolder(
    private val binding: ItemTicketBinding
): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun binding(data: Ticket) {
//        when (data.state) {
//            "" -> {
//
//            }
//        }
//
//        binding.imgTicketStatus
        Log.e("!!!!","binding")
        binding.apply {
            textTicketName.text = data.title
            textTicketLocation.text = data.place
            textTicketDate.text = "${data.startDate} ~ ${data.endDate}"
            textTicketPrice.text = data.pcseguidance
//            Glide.with(binding.root.context).load(data.poster).into(imgPoster)
        }
    }
}