package com.sypark.openTicket.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.sypark.data.db.entity.OpenTicket
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemViewPagerBinding

class ViewPagerAdapter(private val data: List<OpenTicket>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var binding: ItemViewPagerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_view_pager,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ViewHolder(binding).bind(data[position])
    }

    inner class ViewHolder(binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(data: OpenTicket) {
            binding.textTicketName.text = data.title
            binding.openDate.text = data.ticket_opening_date
            Glide.with(itemView.context)
                .load(data.image_url)
                .into(binding.imgTicket)
        }
    }

    override fun getItemCount(): Int = data.size
}