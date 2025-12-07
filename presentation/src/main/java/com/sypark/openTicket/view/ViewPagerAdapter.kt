package com.sypark.openTicket.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.sypark.data.db.entity.OpenTicket
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemViewPagerBinding

class ViewPagerAdapter(private val data: List<OpenTicket>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    lateinit var binding: ItemViewPagerBinding

    inner class ViewHolder(binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(data: OpenTicket) {
            binding.textTicketName.text = data.title
            binding.textTicketName.visibility = View.VISIBLE
            binding.openDate.text = data.ticket_opening_date
            Glide.with(itemView.context)
                .load(data.image_url)
                .into(binding.imgTicket)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_view_pager,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position % 10])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}