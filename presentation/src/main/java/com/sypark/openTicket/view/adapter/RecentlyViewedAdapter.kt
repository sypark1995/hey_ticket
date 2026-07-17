package com.sypark.openTicket.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sypark.domain.model.Content
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRecentlyViewedBinding

class RecentlyViewedAdapter(
    private val onItemClick: (Content) -> Unit,
) : ListAdapter<Content, RecentlyViewedViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
                return oldItem == newItem
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecentlyViewedViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewedViewHolder {
        val binding = ItemRecentlyViewedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyViewedViewHolder(binding)
    }
}

class RecentlyViewedViewHolder(
    private val binding: ItemRecentlyViewedBinding
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun bind(data: Content, onItemClick: (Content) -> Unit) {
        binding.apply {
            textTicketName.text = data.title
            textTicketLocation.text = data.theater
            textTicketDate.text =
                "${data.startDate}${Common.getDayOfWeek(data.startDate)} ~ ${data.endDate}${
                    Common.getDayOfWeek(data.endDate)
                }"

            when (Common.compareDate(data.startDate, data.endDate)) {
                Common.DateType.BEFORE -> {
                    textTicketStatus.visibility = View.VISIBLE
                    textTicketStatus.setBackgroundResource(R.drawable.round_4_blue)
                    textTicketStatus.setTextColor(
                        ContextCompat.getColor(root.context, R.color.blue_2C70F2)
                    )
                    textTicketStatus.text = Common.calculateDday(data.startDate)
                }

                Common.DateType.START -> {
                    textTicketStatus.visibility = View.VISIBLE
                    textTicketStatus.setBackgroundResource(R.drawable.round_4_green)
                    textTicketStatus.setTextColor(
                        ContextCompat.getColor(root.context, R.color.green_2C70F2)
                    )
                    textTicketStatus.text = "공연 중"
                }

                Common.DateType.FINISH -> {
                    textTicketStatus.visibility = View.VISIBLE
                    textTicketStatus.setBackgroundResource(R.drawable.round_4_gray)
                    textTicketStatus.setTextColor(
                        ContextCompat.getColor(root.context, R.color.gray_55555)
                    )
                    textTicketStatus.text = "종료"
                }

                else -> {
                    textTicketStatus.visibility = View.GONE
                }
            }

            Glide.with(binding.root.context).load(data.poster).into(imgPoster)
            root.setOnClickListener {
                onItemClick(data)
            }
        }
    }
}
