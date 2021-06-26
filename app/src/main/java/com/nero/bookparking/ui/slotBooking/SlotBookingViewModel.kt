package com.nero.bookparking.ui.slotBooking

import androidx.lifecycle.ViewModel
import com.nero.bookparking.repository.ParkingDataRepository
import com.nero.bookparking.repository.SlotBookingDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlotBookingViewModel @Inject constructor(val repository: SlotBookingDataRepository) :
    ViewModel() {

    fun updateSlot(buildingID: String, pillerID: String, boxID: String, userId: String = "u5") {
        repository.updateData(
            buildingID = buildingID,
            pillerID = pillerID,
            boxID = boxID,
            userId = userId
        )

    }
}