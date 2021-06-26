package com.nero.bookparking.ui.slotBooking

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nero.bookparking.R
import com.nero.bookparking.databinding.FragmentSlotBookingBinding


class SlotBookingFragment : Fragment() {
    private var _binding: FragmentSlotBookingBinding? = null
    private val binding get() = _binding!!


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



        binding.apply {

            cv13hrs.setOnClickListener {
                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = getString(R.string.rs20)
                tvHiddenText.visibility = View.GONE

            }

            cv34Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = getString(R.string.rs40)
                tvHiddenText.visibility = View.GONE

            }


            cv57Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                tvCost.text = getString(R.string.rs100)
                tvHiddenText.visibility = View.GONE

            }
            cv7Hrs.setOnClickListener {


                cv13hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv34Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv57Hrs.setCardBackgroundColor(resources.getColor(R.color.white))
                cv7Hrs.setCardBackgroundColor(resources.getColor(R.color.app_red))
                tvCost.text = getString(R.string.rs200)
                tvHiddenText.visibility = View.VISIBLE

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



