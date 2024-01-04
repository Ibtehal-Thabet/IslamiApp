package com.example.islami.ui.home.tabs.radio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islami.R
import com.example.islami.api.model.RadiosItem
import com.example.islami.databinding.ItemRadioBinding
import com.example.islami.service.PlayService

class RadioAdapter(var radioList: List<RadiosItem?>?) :
    RecyclerView.Adapter<RadioAdapter.ViewHolder>() {

    var service = PlayService()
    lateinit var itemBinding: ItemRadioBinding
    var isDark = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = radioList?.get(position)
        itemBinding = holder.itemBinding
        itemBinding.radioName.text = item?.name

        onItemClickPlay.let {
            itemBinding.play.setOnClickListener {
                onItemClickPlay?.onItemClick(position, radioList?.get(holder.layoutPosition))
                if (service.isPlaying) {
                    if (isDark) {
                        itemBinding.play.setImageResource(R.drawable.ic_pause_dark)
                    } else {
                        itemBinding.play.setImageResource(R.drawable.ic_pause)
                    }
                    service.isPlaying = false
                } else {
                    if (isDark) {
                        itemBinding.play.setImageResource(R.drawable.play_dark)
                    } else {
                        itemBinding.play.setImageResource(R.drawable.play)
                    }
                    service.isPlaying = true
                }
            }
        }

        onItemClickPlayNext.let {
            itemBinding.playNext.setOnClickListener {
                if (holder.layoutPosition != radioList?.size) {
                    item = radioList?.get(holder.layoutPosition + 1)
                    onItemClickPlayNext?.onItemClick(
                        position,
                        radioList?.get(holder.layoutPosition + 1)
                    )
                }
            }
        }

        onItemClickPlayPrevious.let {
            itemBinding.playPrevious.setOnClickListener {
                if (holder.layoutPosition > 0) {
                    item = radioList?.get(holder.layoutPosition - 1)
                    onItemClickPlayPrevious?.onItemClick(
                        position,
                        radioList?.get(holder.layoutPosition - 1)
                    )
                }
            }
        }

        if (isDark) {
            itemBinding.play.setImageResource(R.drawable.play_dark)
            itemBinding.playNext.setImageResource(R.drawable.play_next_dark)
            itemBinding.playPrevious.setImageResource(R.drawable.play_previous_dark)
        } else {
            itemBinding.play.setImageResource(R.drawable.play)
            itemBinding.playNext.setImageResource(R.drawable.play_next)
            itemBinding.playPrevious.setImageResource(R.drawable.play_previous)
        }
    }

    override fun getItemCount(): Int = radioList?.size ?: 0

    fun bindChannel(list: List<RadiosItem?>?) {
        radioList = list
        notifyDataSetChanged()
    }

    var onItemClickPlay: OnItemClickListener? = null
    var onItemClickPlayNext: OnItemClickListener? = null
    var onItemClickPlayPrevious: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: RadiosItem?)
    }

    class ViewHolder(val itemBinding: ItemRadioBinding) : RecyclerView.ViewHolder(itemBinding.root)
}