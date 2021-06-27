package com.nero.bookparking.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.nero.bookparking.R
import com.nero.bookparking.dto.parkingDTO.MallItem
import com.nero.bookparking.views.interfaces.OnItemClickListener
import com.nero.bookparking.views.viewHolders.MallItemViewHolder
import java.util.logging.Filter
import java.util.logging.LogRecord

class MallItemAdapter(
    private val list: ArrayList<MallItem>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MallItemViewHolder>(), Filterable {

    var searchList = ArrayList<MallItem>()

    init {
        searchList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MallItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mall_item_layout, parent, false)
        return MallItemViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: MallItemViewHolder, position: Int) {
        val mallItem = list[position]
        holder.setData(mallItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): android.widget.Filter {

        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    searchList = list
                } else {
                    val resultList = ArrayList<MallItem>()

                    for (row in list) {
                        if (row.toString().lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }

                    }
                    searchList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchList = results?.values as ArrayList<MallItem>
                notifyDataSetChanged()
            }

        }
    }

}