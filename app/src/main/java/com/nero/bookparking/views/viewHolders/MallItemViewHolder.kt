package com.nero.bookparking.views.viewHolders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nero.bookparking.R
import com.nero.bookparking.dto.parkingDTO.MallItem
import com.nero.bookparking.views.interfaces.OnItemClickListener
import com.ramotion.foldingcell.FoldingCell

class MallItemViewHolder(itemView: View, private val itemClickListener: OnItemClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private lateinit var mIvImage: ImageView

    private lateinit var mTvMallname: TextView

    private lateinit var mIvImageTitle: ImageView

    private lateinit var mTvMallnameTitle: TextView

    private lateinit var mTvDistance: TextView

    private lateinit var mTvSlotsAvailable: TextView

    private lateinit var foldingCell: FoldingCell

    private lateinit var mBtnBook: Button

    fun setData(mallItem: MallItem) {
        itemView.apply {
            mIvImage = findViewById(R.id.ivImage)
            mTvMallname = findViewById(R.id.tvMallName)
            mIvImageTitle = findViewById(R.id.ivImageTitle)
            mTvMallnameTitle = findViewById(R.id.tvMallNameTitle)
            mTvDistance = findViewById(R.id.tvDistance)
            mTvSlotsAvailable = findViewById(R.id.tvSlotsAvailable)
            foldingCell = findViewById(R.id.folding_cell)
            mBtnBook = findViewById(R.id.btnBook)

            Glide.with(mIvImage).load(mallItem.picture).into(mIvImage)
            mTvMallname.text = mallItem.name
            Glide.with(mIvImageTitle).load(mallItem.picture).into(mIvImageTitle)
            mTvMallnameTitle.text = mallItem.name
            mTvDistance.text = mallItem.distance
            mTvSlotsAvailable.text = mallItem.slotsAvailable.toString() + " slots available"

            foldingCell.setOnClickListener {
                foldingCell.toggle(false)
            }
            mBtnBook.setOnClickListener {
                itemClickListener.onItemClicked(mallItem)
            }
        }

    }
}