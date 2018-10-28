package com.example.owner.nearbyplacesmvvm.mapper

import com.example.owner.nearbyplacekotlin.model.NearbyPlace
import com.example.owner.nearbyplacekotlin.model.NearbyPlaceInfo
import java.util.*

class NearbyPlaceInfoMapper {

    fun mapNearbyPlaceInfo(response: NearbyPlace?): List<NearbyPlaceInfo> {
        val nearbyPlaceList = ArrayList<NearbyPlaceInfo>()

        if (response != null) {
            val nearbyPlaceResult = response.results

            for (result in nearbyPlaceResult) {
                val nearbyPlaceInfo = NearbyPlaceInfo()
                nearbyPlaceInfo.name = (result.name)
                nearbyPlaceInfo.icon = (result.icon)
                nearbyPlaceInfo.vicinity = (result.vicinity)
                nearbyPlaceInfo.latitude = (result.geometry.location.lat)
                nearbyPlaceInfo.longitude = (result.geometry.location.lng)

                var photoLink = ""
                photoLink = result.photos[0].photo_reference

                nearbyPlaceInfo.photo = (photoLink)

                nearbyPlaceList.add(nearbyPlaceInfo)
            }

        }
        return nearbyPlaceList
    }
}
