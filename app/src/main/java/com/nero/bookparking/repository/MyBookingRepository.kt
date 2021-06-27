package com.nero.bookparking.repository

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nero.bookparking.dto.MyBookingDto
import com.nero.bookparking.repository.listenerAndDatabaseModel.ListenerAndDatabaseReference

class MyBookingRepository {

    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "u5"

    private val database = Firebase.database.getReference("users")
    private val parkingDatabaseRef = Firebase.database.getReference("parking")


    val listOfMyBooking = mutableStateOf<List<MyBookingDto>>(arrayListOf())

    fun getAllMyBooking(): ListenerAndDatabaseReference {
        val myBookingDatabaseRef = database.child(currentUserUid).child("myBooking")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val arrayList = arrayListOf<MyBookingDto>()

                for (bookingItemSnapShot in snapshot.children) {
                    val data = bookingItemSnapShot.getValue(MyBookingDto::class.java)
                    data?.id = bookingItemSnapShot?.key ?: "nan"
                    if (data != null) {
                        arrayList.add(
                            data
                        )
                    }
                }
                listOfMyBooking.value = arrayList
            }

            override fun onCancelled(error: DatabaseError) {}

        }

        myBookingDatabaseRef.addValueEventListener(listener)

        return ListenerAndDatabaseReference(database = myBookingDatabaseRef, listener = listener)
    }


    fun checkOut(
        buildingID: String,
        floorID: String,
        pillerID: String,
        boxID: String,
        bookingId: String
    ) {
        val databaseRef =
            parkingDatabaseRef.child(buildingID).child(floorID).child(pillerID).child(boxID)
        val map: MutableMap<String, Any> = HashMap<String, Any>()
        map["available"] = true
        map["user_id"] = "nan"
        databaseRef.updateChildren(map)

        val myBookingDatabaseRef =
            database.child(currentUserUid).child("myBooking").child(bookingId)
        val mapBooking: MutableMap<String, Any> = HashMap<String, Any>()
        mapBooking["isCheckedOut"] = true
        myBookingDatabaseRef.updateChildren(mapBooking)

    }


}