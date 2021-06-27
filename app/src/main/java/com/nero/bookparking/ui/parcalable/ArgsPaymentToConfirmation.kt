package com.nero.bookparking.ui.parcalable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArgsPaymentToConfirmation(
    val floor: String,
    val fromTime: Long,
    val toTime: Long,
    val spot: String
) :
    Parcelable {
}