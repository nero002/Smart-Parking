package com.nero.bookparking.dto

data class MyBookingDto(
    val mallId: String? = null,
    val floorId: String? = null,
    val pillarId: String? = null,
    val boxId: String? = null,
    val time: Long? = null,
    val toTime: Long? = null,
    val checkedOut: Boolean? = null,
    var id: String? = null
)
