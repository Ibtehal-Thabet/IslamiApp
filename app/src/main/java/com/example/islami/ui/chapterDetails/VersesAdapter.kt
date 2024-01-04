package com.example.islami.ui.chapterDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islami.databinding.ItemVerseBinding

class VersesAdapter(val list: List<String>, val ayatNumbers: List<Int>) :
    RecyclerView.Adapter<VersesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVerseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.content.text = list[position]
        holder.binding.ayaNumber.text = ayatNumbers[position].toString()
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: ItemVerseBinding) : RecyclerView.ViewHolder(binding.root)
}