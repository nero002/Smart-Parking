package com.nero.bookparking.repository.listenerAndDatabaseModel

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

data class ListenerAndDatabaseReference(
    val database: DatabaseReference,
    val listener: ValueEventListener
)
