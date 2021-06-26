package com.nero.bookparking.ui.slotBooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.nero.bookparking.R
import com.nero.bookparking.databinding.FragmentSlotBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlotBookingFragment : Fragment() {

    private var _binding: FragmentSlotBookingBinding? = null
    private val binding get() = _binding!!
    val viewModel by viewModels<SlotBookingViewModel>()
    val currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: "u5"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlotBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args by navArgs<SlotBookingFragmentArgs>()
        val data = args.parking

        binding.apply {

            cv13hrs.setOnClickListener {
                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = if (cbValetParking.isChecked) "Rs.100.00" else "Rs.20.00"
                tvHiddenText.visibility = View.GONE

            }

            cv34Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = if (cbValetParking.isChecked) "Rs.100.00" else "Rs.40.00"
                tvHiddenText.visibility = View.GONE

            }


            cv57Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = if (cbValetParking.isChecked) "Rs.150.00" else "Rs.100.00"
                tvHiddenText.visibility = View.GONE

            }
            cv7Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                tvCost.text = if (cbValetParking.isChecked) "Rs.250.00" else "Rs.200.00"
                tvHiddenText.visibility = View.VISIBLE

            }

            cvVisaCardPayment.setOnClickListener {
                findNavController().navigate(SlotBookingFragmentDirections.actionSlotBookingFragmentToVisaPaymentFragment())
            }

            ibNext.setOnClickListener {
                viewModel.updateSlot(
                    buildingID = data.building,
                    pillerID = data.pillor,
                    boxID = data.parkingBox,
                    floorID = data.floor
                )
            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



