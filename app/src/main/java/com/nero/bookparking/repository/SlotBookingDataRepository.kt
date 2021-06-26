package com.nero.bookparking.repository

import android.os.SystemClock
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class SlotBookingDataRepository {
    private val database = Firebase.database
    private val parkingDatabaseRef = database.getReference("parking")


    fun updateData(buildingID: String, pillerID: String, boxID: String, userId: String = "u5") {
        val time = Calendar.getInstance().timeInMillis


        val databaseRef = parkingDatabaseRef.child(buildingID).child(pillerID).child(boxID)

        val map: MutableMap<String, Any> = HashMap<String, Any>()
        map["available"] = false
        map["time"] = time
        map["user_id"] = userId
        databaseRef.updateChildren(map)


    }
}