package com.nero.bookparking.ui.presentation.myBookingsScreen

import androidx.lifecycle.ViewModel
import com.nero.bookparking.repository.MyBookingRepository
import com.nero.bookparking.repository.listenerAndDatabaseModel.ListenerAndDatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyBookingViewModel @Inject constructor(val repository: MyBookingRepository) : ViewModel() {


    val allBooksings = repository.listOfMyBooking


    fun getAllBooking(currentUserUid: String): ListenerAndDatabaseReference {
        return repository.getAllMyBooking(currentUserUid)
    }

    fun checkOut(index: Int, currentUserUid: String) {
        val data = allBooksings.value[index]
        repository.checkOut(
            buildingID = data.mallId ?: "m_1",
            floorID = data.floorId ?: "f1",
            pillerID = data.pillarId ?: "p1",
            boxID = data.boxId ?: "b1",
            bookingId = data.id ?: "1", currentUserUid = currentUserUid
        )
    }


}