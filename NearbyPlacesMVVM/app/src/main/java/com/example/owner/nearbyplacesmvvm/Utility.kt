package com.example.owner.nearbyplacesmvvm

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.widget.Toast

object Utility {

    val BASE_URL = "https://maps.googleapis.com"
    val PLACE_PHOTO_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo"
    val SUCCESS = "SUCCESS"
    val LOADING = "loading"
    val ERROR = "error"
    val RADIUS = "1500"
    private val PHOTO_REFERENCES = "photoreference"
    private val SENSOR = "sensor"
    private val MAX_HEIGHT = "maxheight"
    private val MAX_WIDTH = "maxwidth"
    private val KEY = "key"
    val NEARBY_PLACE = "nearby_place"

    fun getProgressDialog(context: Context, msg: String): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage(msg)
        progressDialog.setCancelable(false)
        return progressDialog
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun makeToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun buildPlacePhotoUrl(imagePath: String): String {
        val builtUri = Uri.parse(PLACE_PHOTO_BASE_URL).buildUpon()
                .appendQueryParameter(PHOTO_REFERENCES, imagePath)
                .appendQueryParameter(SENSOR, "false")
                .appendQueryParameter(MAX_HEIGHT, "1200")
                .appendQueryParameter(MAX_WIDTH, "1200")
                .appendQueryParameter(KEY, BuildConfig.PLACE_API_KEY)
                .build()
        return builtUri.toString()
    }
}