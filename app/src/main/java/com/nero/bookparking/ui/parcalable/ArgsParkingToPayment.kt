package com.nero.bookparking.ui.parcalable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ArgsParkingToPayment(val pillor: String, val parkingBox: String, val building: String) :
    Parcelable