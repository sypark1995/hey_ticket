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
import com.sypark.data.db.entity.Content
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRankingBinding
import com.sypark.openTicket.excensions.hide
import java.text.SimpleDateFormat
import java.util.*

class RankingAdapter(private val onItemClickListener: (String) -> Unit) :
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class RankingViewHolder(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(item: Content, onItemClickListener: (String) -> Unit) {
        binding.apply {
            this.root.setOnClickListener {
                onItemClickListener(item.id)
            }

            when (Common.compareDate(item.startDate, item.endDate)) {
                Common.DateType.BEFORE -> {
                    val startDate =
                        Date(SimpleDateFormat("yyyy-MM-dd").parse(item.startDate)!!.time).time

                    val nowDate = SimpleDateFormat("yyyy-MM-dd").parse(
                        SimpleDateFormat("yyyy-MM-dd").format(
                            Date(System.currentTimeMillis())
                        )
                    )

                    textStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.blue_2C70F2)
                    )

                    try {
                        textStatus.text = "D-${(startDate - nowDate.time) / (24 * 60 * 60 * 1000)}"
                    } catch (e: Exception) {
                        textStatus.text = ""
                    }

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

            textRanking.apply {
                if (item.rank == null) {
                    hide()
                } else {
                    text = item.rank.toString()
                }
            }
            textPlace.text = item.theater
            textTitle.text = item.title
            Glide.with(binding.root.context)
                .load(item.poster)
                .transform(CenterCrop(), RoundedCorners(25))
                .error(R.drawable.icon_default_poster)
                .into(imgPoster)
        }
    }
}