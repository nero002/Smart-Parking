package com.nero.bookparking.dto.parkingDTO

data class ParkingDto(
    val id: String,
    val parkingFloorDto: List<ParkingFloorDto> = arrayListOf()
)