package com.sypark.openTicket.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sypark.data.db.entity.SearchWord
import com.sypark.openTicket.databinding.ItemSearchBinding

class SearchWordListAdapter :
    ListAdapter<SearchWord, WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word)
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
}

class WordViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(text: String?) {

        binding.apply {
            textArea.text = text
        }
    }
}

