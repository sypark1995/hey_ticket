package com.sypark.openTicket.view

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sypark.data.db.entity.Content
import com.sypark.openTicket.Common
import com.sypark.openTicket.databinding.ItemTicketBinding

class PagingAdapter(private val clickListener: (Content) -> Unit) :
    PagingDataAdapter<Content, PagingViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem == newItem
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding(it, clickListener)
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
) : RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun binding(data: Content, clickListener: (Content) -> Unit) {
        binding.apply {
            textTicketName.text = data.title
//            textTicketLocation.text = data.place
            textTicketDate.text =
                "${data.startDate}${Common.getDayOfWeek(data.startDate)} ~ ${data.endDate}${
                    Common.getDayOfWeek(data.endDate)
                }"
//            textTicketPrice.text = data.pcseguidance
            Glide.with(binding.root.context).load(data.poster).into(imgPoster)
            root.setOnClickListener {
                clickListener(data)
            }
        }
    }
}