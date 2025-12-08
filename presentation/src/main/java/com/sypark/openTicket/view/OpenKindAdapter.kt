package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sypark.openTicket.R

class OpenKindAdapter(val serverList: List<String>): RecyclerView.Adapter<MyViewHolder>() {

    private var selectCheck: ArrayList<Int> = arrayListOf()

    init {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_kind_open,parent,false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = serverList[position]
    }

    override fun getItemCount(): Int {
        return serverList.size
    }
}

class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}