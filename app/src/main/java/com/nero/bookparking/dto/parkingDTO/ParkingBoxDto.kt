package com.nero.bookparking.dto.parkingDTO

data class ParkingBoxDto(
    var id: String? = null,
    val time: Long? = null,
    val user_id: String? = null,
    val available: Boolean? = false
)
