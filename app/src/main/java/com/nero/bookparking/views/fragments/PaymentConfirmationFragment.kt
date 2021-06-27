package com.nero.bookparking.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nero.bookparking.R
import com.nero.bookparking.databinding.FragmentLocationBinding
import com.nero.bookparking.databinding.FragmentPaymentConfirmationBinding

class PaymentConfirmationFragment : Fragment() {

    private var _binding: FragmentPaymentConfirmationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentConfirmationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment




        return binding.root
    }

}