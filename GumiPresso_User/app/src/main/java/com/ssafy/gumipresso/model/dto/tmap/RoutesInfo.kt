package com.ssafy.gumipresso.model.dto.tmap

data class RoutesInfo(
    val departure: Departure,
    val destination: Destination,
    val predictionTime: String,
    val predictionType: String
)