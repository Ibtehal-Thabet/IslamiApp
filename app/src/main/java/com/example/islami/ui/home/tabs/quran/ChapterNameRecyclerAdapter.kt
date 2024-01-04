package com.example.islami.ui.home.tabs.quran

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islami.databinding.ItemChapterNameBinding

class ChapterNameRecyclerAdapter(
    private val names: List<String>,
    private val verses: List<String>
) :
    RecyclerView.Adapter<ChapterNameRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemChapterNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.title.text = names[position]
        holder.viewBinding.number.text = verses[position]
        if (onItemClickListener != null) {
            holder.viewBinding.root
                .setOnClickListener {
                    onItemClickListener?.onItemClick(position, names[position], verses[position])
                }
        }
    }

    override fun getItemCount(): Int = names.size

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, name: String, verses: String)
    }

    class ViewHolder(val viewBinding: ItemChapterNameBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }

}