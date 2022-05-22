package com.ssafy.gumipresso.model.dto.tmap

data class Properties(
    val arrivalTime: String,
    val departureTime: String,
    val description: String,
    val distance: Int,
    val facilityType: Int,
    val index: Int,
    val lineIndex: Int,
    val name: String,
    val nextRoadName: String,
    val pointIndex: Int,
    val pointType: String,
    val roadType: Int,
    val taxiFare: Int,
    val time: Int,
    val totalDistance: Int,
    val totalFare: Int,
    val totalTime: Int,
    val turnType: Int
)