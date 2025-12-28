package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sypark.data.db.entity.OpenTicket
import com.sypark.openTicket.R

class InfiniteAdapter(val list: List<OpenTicket>) :
    RecyclerView.Adapter<InfiniteAdapter.InfiniteViewHolder>() {

    inner class InfiniteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView_banner = itemView.findViewById<ImageView>(R.id.text_performance_finish)

        fun onBind(data: OpenTicket) {
            Glide.with(itemView.context)
                .load(data.image_url)
                .thumbnail(Glide.with(itemView).load(data.image_url))
                .into(imageView_banner)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfiniteViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_banner, parent, false)
        return InfiniteViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfiniteViewHolder, position: Int) {
        holder.onBind(list[position % 10]) // position에 실제 배너의 개수를 나눈 나머지 값을 사용한다. (여기서는 3개라 하드코딩으로 3을 넣음)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // 아이템의 갯수를 임의로 확 늘린다.
}