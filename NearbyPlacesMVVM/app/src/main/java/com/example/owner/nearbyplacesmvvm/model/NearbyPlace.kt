package com.example.owner.nearbyplacekotlin.model

data class NearbyPlace(
        val html_attributions: List<Any>,
        val next_page_token: String,
        var results: List<Result>,
        val status: String)

//        fun getResults():List<Result> {
//            return results
//        }
