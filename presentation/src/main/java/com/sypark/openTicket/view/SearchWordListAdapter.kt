package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.sypark.data.db.entity.SearchWord
import com.sypark.openTicket.R

class SearchWordListAdapter :
    ListAdapter<SearchWord, SearchWordListAdapter.WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: Chip = itemView.findViewById(R.id.chip_area)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<SearchWord>() {
            override fun areItemsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }

    fun setItem(itemView: View) {
        itemView.findViewById<Chip>(R.id.chip_area).isCloseIconVisible = true
    }
}

