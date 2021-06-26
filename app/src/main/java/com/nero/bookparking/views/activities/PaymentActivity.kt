package com.nero.bookparking.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nero.bookparking.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}