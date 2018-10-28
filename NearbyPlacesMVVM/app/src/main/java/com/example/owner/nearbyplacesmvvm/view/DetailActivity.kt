package com.example.owner.nearbyplacesmvvm.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.owner.nearbyplacekotlin.model.NearbyPlaceInfo
import com.example.owner.nearbyplacesmvvm.R
import com.example.owner.nearbyplacesmvvm.Utility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intent = intent
        val nearbyPlaceInfo = intent.getSerializableExtra(Utility.NEARBY_PLACE) as NearbyPlaceInfo
        place_name.text = nearbyPlaceInfo.name
        place_address.text = nearbyPlaceInfo.vicinity
        Picasso.get().load(Utility.buildPlacePhotoUrl(nearbyPlaceInfo.photo.toString()))
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(photo)

        button.setOnClickListener {
            val lat = (nearbyPlaceInfo.latitude).toString()
            val log = (nearbyPlaceInfo.longitude).toString()
            // Do something in response to button click
            val gmmIntentUri = Uri.parse("geo:$lat,$log")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }


    }

}
