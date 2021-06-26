package com.nero.bookparking.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nero.bookparking.databinding.ActivityLocationBinding
import com.nero.bookparking.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}