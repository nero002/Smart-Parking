package com.nero.bookparking.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nero.bookparking.R
import com.nero.bookparking.dto.parkingDTO.MallItem
import com.nero.bookparking.views.interfaces.OnItemClickListener
import com.nero.bookparking.views.viewHolders.MallItemViewHolder

class MallItemAdapter(private val list: List<MallItem>,private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MallItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MallItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mall_item_layout, parent, false)
        return MallItemViewHolder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: MallItemViewHolder, position: Int) {
        val mallItem = list[position]
        holder.setData(mallItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}