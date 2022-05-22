package com.example.deasa12.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deasa12.R
import com.example.deasa12.databinding.ItemSingerBinding

class SingerAdapter(
    private val singerList: MutableList<String>,
    private val onClickItem: (click: Boolean) -> Unit
): RecyclerView.Adapter<SingerAdapter.SingerViewHolder>() {

    inner class SingerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemSingerBinding.bind(itemView)
        @SuppressLint("ResourceAsColor")
        fun bind(s: String) = with(binding) {
            btnSinger.text = s
            var click = false
            btnSinger.setOnClickListener {
                if (!click) {
                    binding.btnSinger.setTextColor(R.color.gray)
                    click = true
                } else {
                    binding.btnSinger.setTextColor(R.color.black)
                    click = false
                }
                onClickItem.invoke(click)
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