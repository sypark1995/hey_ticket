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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sypark.domain.model.Content
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemDefaultTicketBinding

class MainDefaultAdapter(private val onItemClickListener: (String) -> Unit) :
    ListAdapter<Content, NewTicketViewHolder>(MyItemCallback()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewTicketViewHolder {
        val binding =
            ItemDefaultTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewTicketViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewTicketViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class NewTicketViewHolder(val binding: ItemDefaultTicketBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item: Content, onItemClickListener: (String) -> Unit) {
        binding.apply {
            this.root.setOnClickListener {
                onItemClickListener(item.id)
            }

            textPlace.text = item.theater
            textTitle.text = item.title

            when (Common.compareDate(item.startDate, item.endDate)) {
                Common.DateType.BEFORE -> {
                    textStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.blue_2C70F2)
                    )

                    textStatus.text = Common.calculateDday(item.startDate)

                    textDate.text = Common.genStrDate(item.startDate, "시작")
                }

                Common.DateType.START -> {
                    textStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.green_2C70F2)
                    )

                    textStatus.text = "공연 중"
                    textDate.text = Common.genStrDate(item.endDate, "종료")
                }

                Common.DateType.FINISH -> {
                    textStatus.text = "종료"
                }

                Common.DateType.ERROR -> {
                    textStatus.visibility = View.GONE
                }
            }

            Glide.with(binding.root.context)
                .load(item.poster)
                .transform(CenterCrop(), RoundedCorners(25))
                .error(R.drawable.icon_default_poster)
                .into(imgPoster)
        }
    }
}