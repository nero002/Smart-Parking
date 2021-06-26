package com.nero.bookparking.views.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nero.bookparking.R
import com.nero.bookparking.databinding.ActivityPaymentBinding
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus

class PaymentActivity : AppCompatActivity(), PaymentStatusListener {

    lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transactionId = "TID" + System.currentTimeMillis()
        binding.apply {
            fieldTransactionId.setText(transactionId)
            fieldTransactionRefId.setText(transactionId)
        }

        val easyUpiPayment = EasyUpiPayment(this) {
            this.payeeVpa = "dhiraj02@ybl"
            this.payeeName = "Dheeraj gupta"
            this.payeeMerchantCode = "Q72704588"
//            this.transactionId = "T1234567891234"
//            this.transactionRefId = "T1234567891234"
            this.description = "Description"
            this.amount = "2.00"
        }

        binding.buttonPay.setOnClickListener {
            easyUpiPayment.startPayment()
        }
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        // Transaction Completed
        Log.d("TransactionDetails", transactionDetails.toString())

        binding.textViewStatus.text = transactionDetails.toString()

        when (transactionDetails.transactionStatus) {
            TransactionStatus.SUCCESS -> onTransactionSuccess()
            TransactionStatus.FAILURE -> onTransactionFailed()
            TransactionStatus.SUBMITTED -> onTransactionSubmitted()
        }
    }

    override fun onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Cancelled by user")
    }

    private fun onTransactionSuccess() {
        // Payment Success
        toast("Success")
    }

    private fun onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted")
    }

    private fun onTransactionFailed() {
        // Payment Failed
        toast("Failed")
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}