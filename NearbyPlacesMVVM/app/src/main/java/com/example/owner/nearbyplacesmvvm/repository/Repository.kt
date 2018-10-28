package com.example.owner.nearbyplacesmvvm.repository

import com.example.owner.nearbyplacekotlin.model.NearbyPlace
import com.example.owner.nearbyplacesmvvm.api.ApiCallInterface
import io.reactivex.Observable

class Repository(var apiCallInterface: ApiCallInterface) {

    fun executeGetNearbyPlaces(location: String, radius: String, key: String): Observable<NearbyPlace> {
        return apiCallInterface.getNearbyPlaces(location, radius, key)
    }

}