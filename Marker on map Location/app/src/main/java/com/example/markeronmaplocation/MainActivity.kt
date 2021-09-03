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