package com.nero.bookparking.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.nero.bookparking.databinding.ActivityPhoneNumberSignInBinding
import com.nero.bookparking.helper.PreferenceHelper
import java.util.concurrent.TimeUnit

class PhoneNumberSignInActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhoneNumberSignInBinding

    private var mVerificationId: String? = null
    lateinit var mAuth: FirebaseAuth

    lateinit var mobileNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PreferenceHelper.getSharedPreferences(this)

        mAuth = FirebaseAuth.getInstance()

        binding.btnGetOtp.setOnClickListener {
            if (binding.etMobileNumber.text.count() == 10) {

                binding.etOtp.visibility = View.VISIBLE
                binding.btnVerify.visibility = View.VISIBLE

                mobileNumber = "+91${binding.etMobileNumber.text.toString()}"

                val options = PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber(mobileNumber)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)

            } else {
                Toast.makeText(
                    this,
                    "Enter 10 digits mobile number",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnVerify.setOnClickListener {
            if (!binding.etOtp.text.isNullOrEmpty()) {
                verifyVerificationCode(binding.etOtp.text.toString())
            }
        }


    }

    private val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted:$phoneAuthCredential")
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this@PhoneNumberSignInActivity, e.message, Toast.LENGTH_LONG)
                        .show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(this@PhoneNumberSignInActivity, e.message, Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
            }
        }

    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = mVerificationId?.let { PhoneAuthProvider.getCredential(it, code) }

        //signing the user
        if (credential != null) {
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val i = Intent(this, LocationActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }
            }
    }

}