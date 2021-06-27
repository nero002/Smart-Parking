package com.nero.bookparking.views.interfaces

import com.nero.bookparking.dto.parkingDTO.MallItem

interface OnItemClickListener {

    fun onItemClicked(mallItem: MallItem)

    fun onDirectionsClicked()

}