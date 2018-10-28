package com.example.owner.nearbyplacekotlin.model

data class Result(
        val geometry: Geometry,
        val icon: String,
        val id: String,
        val name: String,
        val opening_hours: OpeningHours,
        val photos: List<Photo>,
        val place_id: String,
        val plus_code: PlusCode,
        val rating: Double,
        val reference: String,
        val scope: String,
        val types: List<String>,
        val vicinity: String
)