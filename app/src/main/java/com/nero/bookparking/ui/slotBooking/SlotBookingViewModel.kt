package com.nero.bookparking.ui.slotBooking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.nero.bookparking.repository.SlotBookingDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlotBookingViewModel @Inject constructor(val repository: SlotBookingDataRepository) :
    ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSlot(
        buildingID: String,
        pillerID: String,
        boxID: String,
        floorID: String,
        currentUserUid: String
    ) {
        repository.updateData2(
            buildingID = buildingID,
            pillerID = pillerID,
            boxID = boxID,
            floorID = floorID,
            currentUserUid = currentUserUid
        )

    }
}