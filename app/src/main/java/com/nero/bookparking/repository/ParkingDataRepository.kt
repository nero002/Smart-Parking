package com.nero.bookparking.repository

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nero.bookparking.dto.parkingDTO.ParkingBoxDto
import com.nero.bookparking.dto.parkingDTO.ParkingDto
import com.nero.bookparking.dto.parkingDTO.ParkingFloorDto
import com.nero.bookparking.dto.parkingDTO.ParkingPillarDto
import com.nero.bookparking.repository.listenerAndDatabaseModel.ListenerAndDatabaseReference


class ParkingDataRepository {

    private val database = Firebase.database
    private val parkingDatabaseRef = database.getReference("parking")

    val parkingDto = mutableStateOf<ParkingDto?>(null)

    fun getAllParkingDataOfBuilding(buildingId: String): ListenerAndDatabaseReference {
        val parkingDatabaseBuildingRef = parkingDatabaseRef.child(buildingId)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                parkingDto.value = snapshot.key?.let { createParkingDto(snapshot, it) }


                try {
                    Log.d("repo", parkingDto.value.toString())
                } catch (e: Exception) {

                }

            }

            override fun onCancelled(error: DatabaseError) {}
        }

        parkingDatabaseBuildingRef.addValueEventListener(listener)

        return ListenerAndDatabaseReference(
            parkingDatabaseBuildingRef,
            listener
        )
    }

    private fun createParkingDto(dataSnapshot: DataSnapshot, id: String): ParkingDto {

        val arrayListOfFloors = arrayListOf<ParkingFloorDto>()

        //creating a list of floors
        for (floorDatabaseSnapshot in dataSnapshot.children) {
            val listOfPillarDto = arrayListOf<ParkingPillarDto>()

            //creating list of pillars
            for (pillarDatabaseSnapShot in floorDatabaseSnapshot.children) {
                //creating a list of parking boxes
                val listOfParkingBoxDto = arrayListOf<ParkingBoxDto>()
                for (parkingBoxDatabaseSnapShot in pillarDatabaseSnapShot.children) {
                    val parkingBox = parkingBoxDatabaseSnapShot.getValue(ParkingBoxDto::class.java)
                    if (parkingBox != null) {
                        parkingBox.id = parkingBoxDatabaseSnapShot.key
                    }
                    if (parkingBox != null) {
                        listOfParkingBoxDto.add(parkingBox)
                    }
                }

                pillarDatabaseSnapShot.key?.let {
                    ParkingPillarDto(
                        id = it,
                        listOfParkingBoxes = listOfParkingBoxDto
                    )
                }?.let {
                    listOfPillarDto.add(
                        it
                    )
                }
            }
            arrayListOfFloors.add(
                ParkingFloorDto(
                    id = floorDatabaseSnapshot.key ?: "nan",
                    parkingPillarDto = listOfPillarDto
                )
            )
        }
        return ParkingDto(
            id = id,
            parkingFloorDto = arrayListOfFloors
        )
    }

}