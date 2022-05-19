package com.example.deasa12.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deasa12.R
import com.example.deasa12.databinding.ItemSingerBinding

class SingerAdapter(
    private val singerList: MutableList<String>,
    private val onClickItem: (singer: String) -> Unit
): RecyclerView.Adapter<SingerAdapter.SingerViewHolder>() {

    inner class SingerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemSingerBinding.bind(itemView)
        fun bind(s: String) = with(binding) {
            tvSinger.text = s

            tvSinger.setOnClickListener {
                onClickItem.invoke(s)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SingerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_singer, parent, false)
    )

    override fun onBindViewHolder(holder: SingerViewHolder, position: Int) {
        holder.bind(singerList[position])
    }

    override fun getItemCount() = singerList.size
}