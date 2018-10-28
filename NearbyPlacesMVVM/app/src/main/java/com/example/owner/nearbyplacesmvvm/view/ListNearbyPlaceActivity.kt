package com.example.owner.nearbyplacesmvvm.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.owner.nearbyplacekotlin.model.NearbyPlace
import com.example.owner.nearbyplacekotlin.model.NearbyPlaceInfo
import com.example.owner.nearbyplacesmvvm.BuildConfig
import com.example.owner.nearbyplacesmvvm.R
import com.example.owner.nearbyplacesmvvm.Utility
import com.example.owner.nearbyplacesmvvm.api.ApiResponse
import com.example.owner.nearbyplacesmvvm.mapper.NearbyPlaceInfoMapper
import com.example.owner.nearbyplacesmvvm.view.adapter.NearbyPlaceAdapter
import com.example.owner.nearbyplacesmvvm.viewModel.ListActivityViewModel
import com.example.owner.nearbyplacesmvvm.viewModel.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ListNearbyPlaceActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var nearbyPlaceInfoMapper: NearbyPlaceInfoMapper

    val MY_PERMISSIONS_REQUEST_LOCATION = 99

    lateinit var viewModel: ListActivityViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var nearbyPlaceInfoList: List<NearbyPlaceInfo> = mutableListOf()

    private val changeObserver = Observer<ApiResponse> { apiResponse ->
        apiResponse?.let { incrementCount(apiResponse) }
    }
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = Utility.getProgressDialog(this, "Please wait...")
        initializeList()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListActivityViewModel::class.java!!)
        changeObserver
        viewModel.getNearbyLocations().observe(this, changeObserver)

        if (Utility.isNetworkAvailable(this) && isLocationPermissionGranted()) {
            getLocationAndMakeRequest()
        }else if(!Utility.isNetworkAvailable(this)){
            Utility.makeToast(this,getString(R.string.no_internet_connection))
        }else if(!isLocationPermissionGranted()){
            Utility.makeToast(this, getString(R.string.you_have_to_grant_permission_to_use))
        }

    }

    private fun initializeList() {
        place_list.layoutManager = LinearLayoutManager(this)
        place_list.hasFixedSize()
        place_list.adapter = NearbyPlaceAdapter(nearbyPlaceInfoList, { partItem: NearbyPlaceInfo -> partItemClicked(partItem) })
    }

    private fun partItemClicked(partItem: NearbyPlaceInfo) {
        Toast.makeText(this, "Clicked: ${partItem.name}", Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Utility.NEARBY_PLACE, partItem)
        startActivity(intent)

    }

    private fun incrementCount(apiResponse: ApiResponse) {
        when (apiResponse.status) {

            Utility.SUCCESS -> {
                progressDialog.dismiss()
                renderSuccessResponse(apiResponse.data)
            }
            Utility.ERROR -> {
                progressDialog.dismiss()
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
            Utility.LOADING ->
                progressDialog.show()

            else -> {

            }
        }
    }

    private fun renderSuccessResponse(data: NearbyPlace?) {
        if (data?.status?.compareTo("OK") != 0) {
            Utility.makeToast(this, "STATUS: " + data?.status)
        } else {
            nearbyPlaceInfoList = nearbyPlaceInfoMapper.mapNearbyPlaceInfo(data)
            place_list.adapter = NearbyPlaceAdapter(nearbyPlaceInfoList, { partItem: NearbyPlaceInfo -> partItemClicked(partItem) })
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocationAndMakeRequest() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location.let {
                        if (Utility.isNetworkAvailable(this)) {
                            viewModel.getNearbyPlaces(location?.latitude.toString() + "," + location?.longitude.toString(), Utility.RADIUS, BuildConfig.PLACE_API_KEY)
                        } else {
                            Utility.makeToast(this, getString(R.string.no_internet_connection))
                            }
                    }
                }
    }

    fun isLocationPermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        })
                        .create()
                        .show()


            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getLocationAndMakeRequest()
                    }

                } else {
                    Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_SHORT).show()


                }
                return
            }
        }
    }

}

