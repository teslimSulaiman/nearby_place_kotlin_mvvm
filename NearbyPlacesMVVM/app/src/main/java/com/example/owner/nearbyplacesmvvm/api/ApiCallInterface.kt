package com.example.owner.nearbyplacesmvvm.api

import com.example.owner.nearbyplacekotlin.model.NearbyPlace
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallInterface {

    @GET("/maps/api/place/nearbysearch/json")
    fun getNearbyPlaces(
            @Query("location") location: String,
            @Query("radius") radius: String,
            @Query("key") key: String): Observable<NearbyPlace>
}