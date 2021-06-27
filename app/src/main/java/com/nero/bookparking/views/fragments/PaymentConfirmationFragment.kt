package com.nero.bookparking.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nero.bookparking.databinding.FragmentPaymentConfirmationBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.abs

class PaymentConfirmationFragment : Fragment() {

    private var _binding: FragmentPaymentConfirmationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentConfirmationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val args by navArgs<PaymentConfirmationFragmentArgs>()

        val data = args.data

        val fromTime: LocalDateTime = Instant.ofEpochMilli(data.fromTime ?: 556456)
            .atZone(ZoneId.systemDefault()).toLocalDateTime()

        val toTime: LocalDateTime = Instant.ofEpochMilli(data.toTime ?: 556456)
            .atZone(ZoneId.systemDefault()).toLocalDateTime()

        binding.apply {
            tvFloor.text = "Floor: ${data.floor}"
            tvSpot.text = "Parking Spot: ${data.spot}"
            tvTime.text = "From ${fromTime.hour}:${fromTime.minute} to ${toTime.hour}:${toTime.minute}"
            tvTimeLeft.text =
                "${abs(toTime.hour - fromTime.hour)} hour ${abs(toTime.minute - fromTime.minute)} minutes left"
            tvDate.text = "${fromTime.dayOfMonth} - ${fromTime.month} - ${fromTime.year}"

            btnMyBookings.setOnClickListener {
                findNavController().navigate(PaymentConfirmationFragmentDirections.actionPaymentConfirmationFragmentToNavMyBookings())
            }

            btnHome.setOnClickListener {
                findNavController().navigate(PaymentConfirmationFragmentDirections.actionPaymentConfirmationFragmentToLocationFragment())
            }
        }

        return binding.root
    }

}