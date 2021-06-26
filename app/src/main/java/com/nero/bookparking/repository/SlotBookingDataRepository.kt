package com.nero.bookparking.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nero.bookparking.dto.MyBookingDto
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class SlotBookingDataRepository {
    private val database = Firebase.database
    private val parkingDatabaseRef = database.getReference("parking")


    /*fun updateData(buildingID: String, pillerID: String, boxID: String, userId: String = "u5") {
        val time = Calendar.getInstance().timeInMillis

        val databaseRef = parkingDatabaseRef.child(buildingID).child(pillerID).child(boxID)

        val map: MutableMap<String, Any> = HashMap<String, Any>()
        map["available"] = false
        map["time"] = time
        map["user_id"] = userId
        databaseRef.updateChildren(map)

    }*/


    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "u5"
    private val userDatabase = Firebase.database.getReference("users")

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData2(
        buildingID: String,
        floorID: String,
        pillerID: String,
        boxID: String,
        hoursToAdd: Long = 1
    ) {

        val time = Calendar.getInstance().timeInMillis


        val hourValue: Long = TimeUnit.HOURS.toMillis(hoursToAdd)
        val toTime: Long = time + hourValue

        val databaseRef =
            parkingDatabaseRef.child(buildingID).child(floorID).child(pillerID).child(boxID)
        val map: MutableMap<String, Any> = HashMap<String, Any>()
        map["available"] = false
        map["time"] = time
        map["user_id"] = currentUserUid
        databaseRef.updateChildren(map)



        val myBookingDatabaseRef =
            userDatabase.child(currentUserUid).child("myBooking").push()

        val data = MyBookingDto(
            mallId = buildingID,
            floorId = floorID,
            pillarId = pillerID,
            boxId = boxID,
            time = time,
            toTime = toTime,
            isCheckedOut = false,
            id = myBookingDatabaseRef.key
        )
        myBookingDatabaseRef.setValue(data)

    }


}