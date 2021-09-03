package com.example.markeronmaplocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class MainActivity : FragmentActivity(), OnMapReadyCallback {
    var currentLocation: Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    // below are the latitude and longitude
    // of 4 different locations.
    var sydney = LatLng(-34.00, 151.00)
    var TamWorth = LatLng(-31.083332, 150.916672)
    var NewCastle = LatLng(-32.916668, 151.750000)
    var Brisbane = LatLng(-27.470125, 153.021072)

    // creating array list for adding all our locations.
    private var locationArrayList: ArrayList<LatLng>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE
            )
            return
        }
        val task: Task<Location> = fusedLocationProviderClient?.getLastLocation()!!
        task.addOnSuccessListener(object : OnSuccessListener<Location?> {
            override fun onSuccess(location: Location?) {
                if (location != null) {
                    currentLocation = location
                    Toast.makeText(
                            applicationContext,
                            currentLocation!!.latitude.toString() + "" + currentLocation!!.longitude,
                            Toast.LENGTH_SHORT
                    ).show()
                    val supportMapFragment: SupportMapFragment =
                            (supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment?)!!
                    supportMapFragment.getMapAsync(this@MainActivity)
/*
                    // in below line we are initializing our array list.
                    locationArrayList = ArrayList()

                    // on below line we are adding our
                    // locations in our array list.

                    locationArrayList?.add(sydney)
                    locationArrayList?.add(TamWorth)
                    locationArrayList?.add(NewCastle)
                    locationArrayList?.add(Brisbane)*/
                }
            }
        })
    }

   override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        //val markerOptions: MarkerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
      //  googleMap.addMarker(markerOptions)
       val melbourne: Marker = googleMap.addMarker(MarkerOptions()
               .position(latLng).title("I am here!"))
       melbourne.showInfoWindow()

    /*   // inside on map ready method
       // we will be displaying all our markers.
       // for adding markers we are running for loop and
       // inside that we are drawing marker on our map.
       // inside on map ready method
       // we will be displaying all our markers.
       // for adding markers we are running for loop and
       // inside that we are drawing marker on our map.
       for (i in locationArrayList!!.indices) {

           // below line is use to add marker to each location of our array list.
           googleMap.addMarker(MarkerOptions().position(locationArrayList!![i]).title("Marker"))

           // below lin is use to zoom our camera on map.
           googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))

           // below line is use to move our camera to the specific location.
           googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!![i]))
       }*/

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 101
    }
}