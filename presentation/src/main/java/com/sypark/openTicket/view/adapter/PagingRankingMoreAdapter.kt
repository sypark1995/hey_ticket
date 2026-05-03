package com.sypark.openTicket.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sypark.data.db.entity.Content
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemRankingMoreBinding
import com.sypark.openTicket.excensions.hide
import java.text.SimpleDateFormat
import java.util.*

class RankingMoreAdapter(private val onItemClickListener: (String) -> Unit) :
    PagingDataAdapter<Content, RankingMoreViewHolder>(diffCallback) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingMoreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RankingMoreViewHolder(
            ItemRankingMoreBinding.inflate(layoutInflater, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RankingMoreViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onItemClickListener)
        }
    }

}

class RankingMoreViewHolder(
    val binding: ItemRankingMoreBinding
) : RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    fun bind(item: Content, onItemClickListener: (String) -> Unit) {
        binding.apply {
            Glide.with(binding.root.context)
                .load(item.poster)
                .transform(CenterCrop(), RoundedCorners(25))
                .error(R.drawable.icon_default_poster)
                .into(imgPoster)

            when (Common.compareDate(item.startDate, item.endDate)) {
                Common.DateType.BEFORE -> {
                    val startDate =
                        Date(SimpleDateFormat("yyyy-MM-dd").parse(item.startDate)!!.time).time

                    val nowDate = SimpleDateFormat("yyyy-MM-dd").parse(
                        SimpleDateFormat("yyyy-MM-dd").format(
                            Date(System.currentTimeMillis())
                        )
                    )

                    if (nowDate == null) {
                        textState.text = ""
                    } else {
                        textState.apply {
                            setBackgroundResource(R.drawable.round_4_blue)
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.blue_2C70F2
                                )
                            )

                            text = context.getString(
                                R.string.state_before,
                                ((startDate - nowDate.time) / (24 * 60 * 60 * 1000))
                            )
                        }
                    }
                }

                Common.DateType.START -> {
                    textState.apply {
                        setBackgroundResource(R.drawable.round_4_green)
                        setTextColor(
                            ContextCompat.getColor(context, R.color.green_2C70F2)
                        )
                        text = context.getString(
                            R.string.state_during
                        )
                    }
                }

                Common.DateType.FINISH -> {
                    textState.apply {
                        setBackgroundResource(R.drawable.round_4_gray)
                        setTextColor(
                            ContextCompat.getColor(context, R.color.gray_55555)
                        )
                        text = context.getString(
                            R.string.state_finish
                        )
                    }
                }

                Common.DateType.ERROR -> {
                    textState.hide()
                }
            }

            textRanking.text = item.rank.toString()
            textPlace.text = item.theater
            textTitle.text = item.title
            textPriceStandard.text = item.price

            if ((item.startDate.isEmpty() || item.endDate.isEmpty())) {
                textDate.hide()
            } else {
                textDate.text =
                    "${item.startDate}${Common.getDayOfWeek(item.startDate)} ~ ${item.endDate}${
                        Common.getDayOfWeek(item.endDate)
                    }"
            }

            root.setOnClickListener {
                onItemClickListener(item.id)
            }
        }
    }
}