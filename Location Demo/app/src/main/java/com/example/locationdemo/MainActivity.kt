package com.example.locationdemo

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var permissionsToRequest: ArrayList<Any>? = null
    private val permissionsRejected: ArrayList<Any> = ArrayList()
    private val permissions: ArrayList<Any>  = ArrayList()
    var locationTrack: GPSTracker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissions.add(ACCESS_FINE_LOCATION)
        permissions.add(ACCESS_COARSE_LOCATION)
        permissionsToRequest = findUnAskedPermissions(permissions!!)
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest!!.size > 0) requestPermissions(
                permissionsToRequest!!.toArray(
                    arrayOfNulls<String>(permissionsToRequest!!.size)
                ), ALL_PERMISSIONS_RESULT
            )
        }
        btn.setOnClickListener(object : View.OnClickListener {
         override   fun onClick(view: View?) {
                locationTrack = GPSTracker(this@MainActivity)
                if (locationTrack!!.canGetLocation()) {
                    val longitude = locationTrack!!.getLongitude()!!
                    val latitude = locationTrack!!.getLatitude()!!
                    Toast.makeText(
                        applicationContext,
                        """
                            Longitude:${java.lang.Double.toString(longitude)}
                            Latitude:${java.lang.Double.toString(latitude)}
                            """.trimIndent(), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    locationTrack!!.showSettingsAlert()
                }
            }
        })
    }

    private fun findUnAskedPermissions(wanted: ArrayList<Any>): ArrayList<Any> {
        val result = ArrayList<Any>()
        for (perm in wanted) {
            if (!hasPermission(perm.toString())) {
                result.add(perm)
            }
        }
        return result
    }

    private fun hasPermission(permission: Any): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(permission.toString()) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }

    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ALL_PERMISSIONS_RESULT -> {
                for (perms in permissionsToRequest!!) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms)
                    }
                }
                if (permissionsRejected.size > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0).toString())) {
                            showMessageOKCancel(
                                "These permissions are mandatory for the application. Please allow access."
                            ) { dialog, which ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                        permissionsRejected.toArray(
                                            arrayOfNulls<String>(
                                                permissionsRejected.size
                                            )
                                        ), ALL_PERMISSIONS_RESULT
                                    )
                                }
                            }
                            return
                        }
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //locationTrack!!.stopListener()
    }

    companion object {
        private const val ALL_PERMISSIONS_RESULT = 101
    }
}
