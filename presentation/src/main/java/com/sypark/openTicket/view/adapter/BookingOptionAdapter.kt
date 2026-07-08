package com.sypark.openTicket.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.databinding.ItemBookingOptionBinding
import com.sypark.openTicket.model.BookingVendor

class BookingOptionAdapter(private val onItemClick: (BookingVendor) -> Unit) :
    ListAdapter<BookingVendor, BookingOptionViewHolder>(
        object : DiffUtil.ItemCallback<BookingVendor>() {
            override fun areItemsTheSame(oldItem: BookingVendor, newItem: BookingVendor) = oldItem == newItem
            override fun areContentsTheSame(oldItem: BookingVendor, newItem: BookingVendor) = oldItem == newItem
        }
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingOptionViewHolder {
        val binding = ItemBookingOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingOptionViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

class BookingOptionViewHolder(private val binding: ItemBookingOptionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(vendor: BookingVendor, onItemClick: (BookingVendor) -> Unit) {
        binding.textVendorName.text = vendor.displayName
        binding.root.setOnClickListener { onItemClick(vendor) }
    }
}
