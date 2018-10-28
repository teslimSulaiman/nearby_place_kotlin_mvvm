package com.example.owner.nearbyplacekotlin.model

import java.io.Serializable

class NearbyPlaceInfo : Serializable {

    var name: String? = null
    var icon: String? = null
    var photo: String? = null
    var vicinity: String? = null
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()


}
