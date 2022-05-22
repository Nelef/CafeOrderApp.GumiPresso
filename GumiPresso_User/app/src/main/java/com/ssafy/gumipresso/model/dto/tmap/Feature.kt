package com.ssafy.gumipresso.model.dto.tmap

data class Feature(
    val geometry: GeometryX,
    val properties: Properties,
    val type: String
)