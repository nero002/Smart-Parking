package com.nero.bookparking.dto.parkingDTO

data class ParkingFloorDto(
    val id: String,
    val parkingPillarDto: List<ParkingPillarDto>
)
