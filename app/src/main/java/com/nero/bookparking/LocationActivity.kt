package com.nero.bookparking

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng
import java.lang.String
import android.content.DialogInterface

import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog


class LocationActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var mLatitudeTextView: TextView? = null
    private var mLongitudeTextView: TextView? = null
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mLocation: Location
    lateinit var mLocationManager: LocationManager
    lateinit var mLocationRequest: LocationRequest

    lateinit var locationListener: LocationListener

    private val UPDATE_INTERVAL = (2 * 1000 /* 10 secs */).toLong()
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    lateinit var locationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

//        val locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this)

        mLatitudeTextView = findViewById<TextView>(R.id.tvLat)
        mLongitudeTextView = findViewById<TextView>(R.id.tvLong)
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mLocationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager

    }

    override fun onLocationChanged(location: Location) {
        val msg = "Updated Location: " +
                java.lang.Double.toString(location.latitude) + "," +
                java.lang.Double.toString(location.longitude)
        mLatitudeTextView!!.text = String.valueOf(location.latitude)
        mLongitudeTextView!!.text = String.valueOf(location.longitude)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        // You can now create a LatLng Object for use with maps
        // You can now create a LatLng Object for use with maps
        val latLng = LatLng(location.latitude, location.longitude)
    }

    override fun onConnected(p0: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {
            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient.connect()
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + p0.errorCode)
    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect();
        }
    }

    private fun startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient,
            mLocationRequest, this
        )
        Log.d("reque", "--->>>>")
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled()) showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
            .setMessage(
                """
            Your Locations Settings is set to 'Off'.
            Please Enable Location to use this app
            """.trimIndent()
            )
            .setPositiveButton("Location Settings",
                DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}