package com.ucmart.ui.cart.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItem(
    val productId: Int,
    val name: String? = null,
    val imgUrl: String? = null,
    val unit: String? = null,
    var pricePerUnit: Int = 0,
    var quantity: Int = 0
): Parcelable