package com.nero.bookparking.ui.presentation.parkingSpotBookingScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nero.bookparking.repository.ParkingDataRepository
import com.nero.bookparking.repository.listenerAndDatabaseModel.ListenerAndDatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingScreenViewModel @Inject constructor(val repository: ParkingDataRepository) :
    ViewModel() {


    val listOfData = repository.parkingDto

    val currentSelectede = mutableStateOf("")

    val currentSelectedPillar = mutableStateOf("")

    val currentFloor = mutableStateOf(0)


    fun getParkingData(buildingId: String): ListenerAndDatabaseReference {
        return repository.getAllParkingDataOfBuilding(buildingId)
    }

}