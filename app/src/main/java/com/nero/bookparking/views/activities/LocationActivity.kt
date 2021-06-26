package com.nero.bookparking.views.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nero.bookparking.R
import com.nero.bookparking.databinding.ActivityLocationBinding
import com.nero.bookparking.dto.parkingDTO.MallItem
import com.nero.bookparking.views.OnItemClickListener
import com.nero.bookparking.views.adapters.MallItemAdapter
import java.util.*


class LocationActivity : AppCompatActivity(),OnItemClickListener {

    private val FINE_LOCATION_RO = 101
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var binding: ActivityLocationBinding

    lateinit var mallItemAdapter: MallItemAdapter
    private var mallItemList = arrayListOf<MallItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        checkForPermissions(Manifest.permission.ACCESS_FINE_LOCATION, "location", FINE_LOCATION_RO)

        buildData()
        mallItemAdapter = MallItemAdapter(mallItemList,this)
        binding.rvMallItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mallItemAdapter
        }
    }

    private fun buildData() {
        mallItemList.add(
            MallItem(R.drawable.manjeera_mall_jntu, "Manjeera Trinity Mall, JNTU", "9 km away", 56)
        )
        mallItemList.add(
            MallItem(R.drawable.gvk_one_mall, "GVK One Mall", "15.5 km away", 107)
        )
        mallItemList.add(
            MallItem(R.drawable.forum_sujana_mall, "Forum Srujana Mall, JNTU", "9.5 km away", 54)
        )
        mallItemList.add(
            MallItem(
                R.drawable.city_centre_mall,
                "City Center Mall, Banjara Hills",
                "15 km away",
                86
            )
        )
        mallItemList.add(
            MallItem(
                R.drawable.hyderabad_central_mall,
                "Hyderabad Central Mall, JNTU",
                "14 km away",
                42
            )
        )
        mallItemList.add(
            MallItem(R.drawable.inorbit_mall_madhapur, "Inorbit Mall, Madhapur", "12 km away", 10)
        )

        mallItemList.add(
            MallItem(
                R.drawable.sarath_city_mall,
                "Sarath City Capital Mall, Kondapur",
                "13 km away",
                96
            )
        )
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                    permission,
                    name,
                    requestCode
                )
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }


    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
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
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            val location = it.result
            if (location != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addressList = geocoder.getFromLocation(
                    location.latitude, location.longitude, 1
                )
                Toast.makeText(this, addressList[0].locality.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.setData(uri)
                startActivity(intent)
            } else {
                getLocation()
            }
        }
        when (requestCode) {
            FINE_LOCATION_RO -> innerCheck("location")
        }
    }


    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("permission to access your $name is requested to use this app")
            setTitle("permission requested")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@LocationActivity,
                    arrayOf(permission), requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()

    }

    override fun onItemClicked(mallItem: MallItem) {

    }
}