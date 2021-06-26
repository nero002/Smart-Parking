package com.nero.bookparking.views.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nero.bookparking.R
import com.nero.bookparking.databinding.FragmentLocationBinding
import com.nero.bookparking.dto.parkingDTO.MallItem
import com.nero.bookparking.views.adapters.MallItemAdapter
import com.nero.bookparking.views.interfaces.OnItemClickListener
import java.util.*

class LocationFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val FINE_LOCATION_RO = 101
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var mallItemAdapter: MallItemAdapter
    private var mallItemList = arrayListOf<MallItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        getLocation()

        buildData()
        mallItemAdapter = MallItemAdapter(mallItemList, this)
        binding.rvMallItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mallItemAdapter
        }



        return binding.root
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

    private fun getLocation() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
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
                val geocoder = Geocoder(context, Locale.getDefault())
                val addressList = geocoder.getFromLocation(
                    location.latitude, location.longitude, 1
                )
                Toast.makeText(context, addressList[0].locality.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onItemClicked(mallItem: MallItem) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}