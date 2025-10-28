package com.sypark.openTicket.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sypark.openTicket.R
import com.sypark.openTicket.databinding.ItemViewPagerBinding
import com.sypark.openTicket.model.MelonTicket
import com.sypark.openTicket.model.Ticket
import java.util.ArrayList

class ViewPagerAdapter(private val data: ArrayList<MelonTicket>) :
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

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(data: MelonTicket) {
            binding.textTicketName.text = data.title
            binding.openDate.text = data.openDate
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(binding.imgTicket)
        }
    }
}