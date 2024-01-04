package com.example.islami.ui.home.tabs.hadeeth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islami.databinding.ItemHadeethBinding
import com.example.islami.ui.model.Hadeeth

class HadeethNameRecyclerAdapter(private var items: List<Hadeeth>?) :
    RecyclerView.Adapter<HadeethNameRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemHadeethBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.title.text = items!![position].title

        onItemClickListener.let {
            holder.viewBinding.root
                .setOnClickListener {
                    onItemClickListener?.onItemClick(position, items!![position])
                }
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    fun bindItems(newList: List<Hadeeth>) {
        items = newList
        notifyDataSetChanged()
    }


    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: Hadeeth)
    }

    class ViewHolder(val viewBinding: ItemHadeethBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {}
}